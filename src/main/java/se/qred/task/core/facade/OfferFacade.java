package se.qred.task.core.facade;

import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.api.response.ApplicationFullResponse;
import se.qred.task.api.response.OfferCreateResponse;
import se.qred.task.api.response.OfferFullResponse;
import se.qred.task.core.model.ApplicationPair;
import se.qred.task.core.model.OfferPair;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.ContractService;
import se.qred.task.core.service.OfferService;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;

public class OfferFacade {

    private final ApplicationService applicationService;
    private final OfferService offerService;
    private final ContractService contractService;

    public OfferFacade(ApplicationService applicationService, OfferService offerService, ContractService contractService) {
        this.applicationService = applicationService;
        this.offerService = offerService;
        this.contractService = contractService;
    }


    public Response createOffer(User user, Long applicationId, OfferCreateRequest offerCreateRequest) {
        if (!user.isAccountManager()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (!applicationService.isProcessedApplication(applicationId)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        final Application application = applicationService.getApplicationById(applicationId);
        final OfferPair offerPair = offerService.create(offerCreateRequest, application);
        applicationService.pendApplication(application, offerPair.getOffer());
        return Response.ok(offerPair.getOfferCreateResponse()).build();
    }

    public Response negotiateLatestOfferByUser(User user, OfferNegotiateUserRequest offerRequest) {
        final ApplicationFullResponse latestApplication = applicationService.getLatestApplication(user.getId());
        final OfferFullResponse negotiatedOffer = offerService.negotiate(latestApplication.getId(), offerRequest);
        // TODO Negotiate history
        return Response.accepted(negotiatedOffer).build();
    }

    public Response negotiateOfferByManager(User user, String applicationId, OfferNegotiateManagerRequest offerRequest) {
        if (!user.isAccountManager()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        final OfferFullResponse negotiatedOffer = offerService.negotiate(Long.parseLong(applicationId), offerRequest);
        // TODO Negotiate history
        return Response.accepted(negotiatedOffer).build();
    }

    public Response signLatestOffer(User user) {
        final ApplicationPair applicationPair = applicationService.signLatestApplication(user.getId());
        final OfferFullResponse offer = offerService.sign(applicationPair.getApplication().getId());
        contractService.create(offer, user.getId(), applicationPair.getApplication().getOrganization());
        return Response.accepted(applicationPair.getApplicationResponse()).build();
    }
}
