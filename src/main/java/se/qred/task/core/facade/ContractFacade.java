package se.qred.task.core.facade;

import se.qred.task.api.response.ContractResponse;
import se.qred.task.core.service.ContractService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.db.dto.User;

import javax.ws.rs.core.Response;
import java.util.List;

public class ContractFacade {

    private final ContractService contractService;
    private final OrganizationService organizationService;

    public ContractFacade(final ContractService contractService, final OrganizationService organizationService) {
        this.contractService = contractService;
        this.organizationService = organizationService;
    }

    public Response getContractsByUser(final User user) {
        final List<ContractResponse> contracts = contractService.getContractsByUserId(user.getId());
        for (ContractResponse contract : contracts) {
            contract.setOrganization(organizationService.getByOrganizationId(contract.getOrganization().getId()));
        }
        return Response.ok(contracts).build();
    }
}
