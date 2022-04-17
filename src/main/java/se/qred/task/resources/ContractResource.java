package se.qred.task.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.qred.task.core.facade.ContractFacade;
import se.qred.task.db.dto.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContractResource {

    private static final Logger logger  = LoggerFactory.getLogger(ContractResource.class);
    private final ContractFacade contractFacade;

    public ContractResource(ContractFacade contractFacade) {
        this.contractFacade = contractFacade;
    }

    @GET
    @Path("/list")
    @UnitOfWork
    public Response getContracts(@Auth final User user) {
        logger.debug(String.format("Contract list: User: %s", user));
        return contractFacade.getContractsByUser(user);
    }
}
