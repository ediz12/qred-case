package se.qred.task.core.mapper.response;

import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.db.dto.Contract;

public class ContractResponseMapper {

    public ContractResponse map(Contract contract) {
        return new ContractResponse.Builder()
                .id(contract.getId())
                .amount(contract.getAmount())
                .term(contract.getTerm())
                .interest(contract.getInterest())
                .totalCommission(contract.getTotalCommission())
                .totalAmount(contract.getTotalAmount())
                .signedDate(contract.getSignedDate())
                .organization(new OrganizationResponse.Builder()
                        .id(contract.getOrganization().getId())
                        .build())
                .build();
    }
}
