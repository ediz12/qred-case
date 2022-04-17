package se.qred.task.core.facade;

import io.dropwizard.jersey.errors.ErrorMessage;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.client.AllabolagClient;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.core.model.OrganizationPair;
import se.qred.task.core.model.exceptions.AllabolagUnreachableException;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.OfferService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Offer;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

public class ApplicationFacade {

    private final ApplicationService applicationService;
    private final OrganizationService organizationService;
    private final OfferService offerService;
    private final AllabolagClient allabolagClient;

    public ApplicationFacade(ApplicationService applicationService, OrganizationService organizationService, OfferService offerService, AllabolagClient allabolagClient) {
        this.applicationService = applicationService;
        this.organizationService = organizationService;
        this.offerService = offerService;
        this.allabolagClient = allabolagClient;
    }

    public Response createApplication(final User user, final ApplicationApplyRequest applyRequest) {
        final Optional<OrganizationPair> optionalPair = getOrganizationPair(applyRequest.getOrganizationNumber());
        if (!optionalPair.isPresent()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Allabolag API is not reachable"))
                    .build();
        }
        final OrganizationPair organizationPair = optionalPair.get();
        cancelOldApplicationAndOffer(user.getId());
        final ApplicationApplyResponse applicationApplyResponse = applicationService.create(applyRequest, organizationPair.getOrganization(), user.getId());
        applicationApplyResponse.setOrganization(organizationPair.getOrganizationResponse());

        return Response.ok(applicationApplyResponse).build();
    }

    public Response getLatestApplicationByUser(final User user) {
        final ApplicationFullResponse application = applicationService.getLatestApplication(user.getId());
        final OrganizationResponse organization = organizationService.getByOrganizationId(application.getOrganization().getId());
        application.setOrganization(organization);
        if (Objects.nonNull(application.getOffer())) {
            offerService.getOfferById(application.getOffer().getId()).ifPresent(application::setOffer);
        }
        return Response.ok(application).build();
    }

    private Optional<OrganizationPair> getOrganizationPair(String organizationNumber) {
        final Optional<OrganizationPair> optionalOrganization = organizationService.getByOrganizationNumber(organizationNumber);
        if (optionalOrganization.isPresent()) {
            return optionalOrganization;
        }
        return createOrganization(organizationNumber);
    }

    private Optional<OrganizationPair> createOrganization(final String organizationNumber) {
        try {
            final AllabolagOrganizationDetailResponse allabolagOrganizationResponse = allabolagClient.getOrganizationDetails(organizationNumber);
            return Optional.of(organizationService.create(allabolagOrganizationResponse));
        } catch (AllabolagUnreachableException e) {
            return Optional.empty();
        }
    }

    private void cancelOldApplicationAndOffer(final String userId) {
        final Optional<Application> optionalApplication = applicationService.cancelLatestApplication(userId);
        if (!optionalApplication.isPresent()) {
            return;
        }
        Offer offer = optionalApplication.get().getOffer();
        if (Objects.nonNull(offer)) {
            offerService.cancel(offer);
        }
    }
}
