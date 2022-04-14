package se.qred.task.core.facade;

import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.response.ApplicationApplyResponse;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.client.AllabolagClient;
import se.qred.task.client.model.AllabolagOrganizationDetailResponse;
import se.qred.task.core.model.OrganizationPair;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.OfferService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;
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
        final OrganizationPair organizationPair = getOrganizationPair(applyRequest.getOrganizationNumber());

        cancelOldApplicationAndOffer(user.getId());
        final ApplicationApplyResponse applicationApplyResponse = applicationService.create(applyRequest, organizationPair.getOrganization(), user.getId());
        applicationApplyResponse.setOrganization(organizationPair.getOrganizationResponse());

        return Response.ok(applicationApplyResponse).build();
    }

    public Response getLatestApplicationByUser(final User user) {
        final ApplicationFullResponse application = applicationService.getLatestApplication(user.getId());
        final OrganizationResponse organization = organizationService.getByOrganizationId(application.getOrganization().getId());
        application.setOrganization(organization);
        offerService.getOfferByApplicationId(application.getId()).ifPresent(application::setOffer);
        return Response.ok(application).build();
    }

    private OrganizationPair getOrganizationPair(String organizationNumber) {
        final Optional<OrganizationPair> optionalOrganization = organizationService.getByOrganizationNumber(organizationNumber);
        return optionalOrganization.orElseGet(() -> createOrganization(organizationNumber));
    }

    private OrganizationPair createOrganization(final String organizationNumber) {
        final AllabolagOrganizationDetailResponse allabolagOrganizationResponse = allabolagClient.getOrganizationDetails(organizationNumber);
        return organizationService.create(allabolagOrganizationResponse);
    }

    private void cancelOldApplicationAndOffer(final String userId) {
        final Optional<Long> optionalApplicationId = applicationService.cancelLatestApplication(userId);
        optionalApplicationId.ifPresent(offerService::cancel);
    }
}
