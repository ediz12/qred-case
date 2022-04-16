package se.qred.task.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
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
        return applicationFacade.createApplication(user, applicationApplyRequest);
    }

    @GET
    @Path("/latest")
    @UnitOfWork
    public Response getLatestApplication(@Auth final User user) {
        return applicationFacade.getLatestApplicationByUser(user);
    }

    @POST
    @Path("/{applicationId}/offer")
    @UnitOfWork
    public Response createApplicationOffer(@Auth final User user, @PathParam("applicationId") String applicationId, @Valid final OfferCreateRequest offerCreateRequest) {
        return offerFacade.createOffer(user, Long.parseLong(applicationId), offerCreateRequest);
    }

    @PUT
    @Path("/latest/offer/negotiate")
    @UnitOfWork
    public Response negotiateLatestApplicationOffer(@Auth final User user, @Valid final OfferNegotiateUserRequest offerRequest) {
        return offerFacade.negotiateLatestOfferByUser(user, offerRequest);
    }

    @PUT
    @Path("/{applicationId}/offer/negotiate")
    @UnitOfWork
    public Response negotiateApplicationOffer(@Auth final User user, @PathParam("applicationId") String applicationId, @Valid final OfferNegotiateManagerRequest offerRequest) {
        return offerFacade.negotiateOfferByManager(user, Long.parseLong(applicationId), offerRequest);
    }

    @PUT
    @Path("/latest/offer/sign")
    @UnitOfWork
    public Response signLatestApplicationOffer(@Auth final User user) {
        return offerFacade.signLatestOffer(user);
    }
}
