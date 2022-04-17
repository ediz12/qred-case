package se.qred.task.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.qred.task.api.request.ApplicationApplyRequest;
import se.qred.task.api.request.OfferCreateRequest;
import se.qred.task.api.request.OfferNegotiateManagerRequest;
import se.qred.task.api.request.OfferNegotiateUserRequest;
import se.qred.task.core.facade.ApplicationFacade;
import se.qred.task.core.facade.OfferFacade;
import se.qred.task.db.dto.User;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/applications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApplicationResource {

    private static final Logger logger  = LoggerFactory.getLogger(ApplicationResource.class);
    private final ApplicationFacade applicationFacade;
    private final OfferFacade offerFacade;

    public ApplicationResource(ApplicationFacade applicationFacade, OfferFacade offerFacade) {
        this.applicationFacade = applicationFacade;
        this.offerFacade = offerFacade;
    }

    @POST
    @Path("/apply")
    @UnitOfWork
    public Response createApplication(@Auth final User user, @Valid final ApplicationApplyRequest applicationApplyRequest) {
        logger.debug(String.format("Create Application: User: %s, ApplyRequest: %s", user, applicationApplyRequest));
        return applicationFacade.createApplication(user, applicationApplyRequest);
    }

    @GET
    @Path("/latest")
    @UnitOfWork
    public Response getLatestApplication(@Auth final User user) {
        logger.debug(String.format("Get Latest Application: User: %s", user));
        return applicationFacade.getLatestApplicationByUser(user);
    }

    @POST
    @Path("/{applicationId}/offer")
    @UnitOfWork
    public Response createApplicationOffer(@Auth final User user, @PathParam("applicationId") String applicationId, @Valid final OfferCreateRequest offerCreateRequest) {
        logger.debug(String.format("Create Application Offer: User: %s, applicationId: %s, Create request: %s", user, applicationId, offerCreateRequest));
        return offerFacade.createOffer(user, Long.parseLong(applicationId), offerCreateRequest);
    }

    @PUT
    @Path("/latest/offer/negotiate")
    @UnitOfWork
    public Response negotiateLatestApplicationOffer(@Auth final User user, @Valid final OfferNegotiateUserRequest offerRequest) {
        logger.debug(String.format("Negotiate User Offer: User: %s, Negotiate Request: %s", user, offerRequest));
        return offerFacade.negotiateLatestOfferByUser(user, offerRequest);
    }

    @PUT
    @Path("/{applicationId}/offer/negotiate")
    @UnitOfWork
    public Response negotiateApplicationOffer(@Auth final User user, @PathParam("applicationId") String applicationId, @Valid final OfferNegotiateManagerRequest offerRequest) {
        logger.debug(String.format("Negotiate Manager Offer: User: %s, Application Id: %s, Negotiate Request: %s", user, applicationId, offerRequest));
        return offerFacade.negotiateOfferByManager(user, Long.parseLong(applicationId), offerRequest);
    }

    @PUT
    @Path("/latest/offer/sign")
    @UnitOfWork
    public Response signLatestApplicationOffer(@Auth final User user) {
        logger.debug(String.format("Sign Latest Application Offer: User: %s", user));
        return offerFacade.signLatestOffer(user);
    }
}
