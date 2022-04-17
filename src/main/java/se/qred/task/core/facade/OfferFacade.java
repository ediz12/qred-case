package se.qred.task.core.facade;

import io.dropwizard.jersey.errors.ErrorMessage;
import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.core.model.ApplicationPair;
import se.qred.task.core.model.OfferPair;
import se.qred.task.core.model.exceptions.OfferAlreadyNegotiatedException;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.ContractService;
import se.qred.task.core.service.OfferService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;

public class OfferFacade {

    private final ApplicationService applicationService;
    private final OrganizationService organizationService;
    private final OfferService offerService;
    private final ContractService contractService;

    public OfferFacade(ApplicationService applicationService, OrganizationService organizationService, OfferService offerService, ContractService contractService) {
        this.applicationService = applicationService;
        this.organizationService = organizationService;
        this.offerService = offerService;
        this.contractService = contractService;
    }


    public Response createOffer(User user, Long applicationId, OfferCreateRequest offerCreateRequest) {
        if (!user.isAccountManager()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "User is not a manager!"))
                    .build();
        }

        if (!applicationService.isProcessedApplication(applicationId)) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), "This application does not exist or is already processed!"))
                    .build();
        }

        final Application application = applicationService.getApplicationById(applicationId);
        final OfferPair offerPair = offerService.create(offerCreateRequest);
        applicationService.pendApplication(application, offerPair.getOffer());
        OfferCreateResponse offerCreateResponse = offerPair.getOfferCreateResponse();
        offerCreateResponse.setApplicationId(applicationId);
        return Response.ok(offerCreateResponse).build();
    }

    public Response negotiateLatestOfferByUser(User user, OfferNegotiateUserRequest offerRequest) {
        final ApplicationFullResponse latestApplication = applicationService.getLatestApplication(user.getId());
        try {
            final OfferFullResponse negotiatedOffer = offerService.negotiate(latestApplication.getOffer().getId(), offerRequest);
            return Response.accepted(negotiatedOffer).build();
        } catch (OfferAlreadyNegotiatedException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), "Offer is already negotiated by the user!"))
                    .build();
        }
        // TODO Negotiate history
    }

    public Response negotiateOfferByManager(User user, Long applicationId, OfferNegotiateManagerRequest offerRequest) {
        if (!user.isAccountManager()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "User is not a manager!"))
                    .build();
        }

        Application application = applicationService.getApplicationById(applicationId);

        try {
            final OfferFullResponse negotiatedOffer = offerService.negotiate(application.getOffer().getId(), offerRequest);
            return Response.accepted(negotiatedOffer).build();
        } catch (OfferAlreadyNegotiatedException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorMessage(Response.Status.CONFLICT.getStatusCode(), "Offer is already negotiated by the manager!"))
                    .build();
        }
        // TODO Negotiate history
    }

    public Response signLatestOffer(User user) {
        final ApplicationPair applicationPair = applicationService.signLatestApplication(user.getId());
        final OfferFullResponse offer = offerService.sign(applicationPair.getApplication().getOffer().getId());
        contractService.create(offer, user.getId(), applicationPair.getApplication().getOrganization());

        ApplicationFullResponse applicationResponse = applicationPair.getApplicationResponse();
        applicationResponse.setOrganization(organizationService.getByOrganizationId(applicationResponse.getOrganization().getId()));
        applicationResponse.setOffer(offer);
        return Response.accepted(applicationResponse).build();
    }
}
