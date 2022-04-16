package se.qred.task.base.model.response;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import se.qred.task.api.response.ContractResponse;
import se.qred.task.api.response.OrganizationResponse;
import se.qred.task.base.model.db.MockOffer;
import se.qred.task.base.model.db.MockOrganization;

import java.util.List;

public class MockContractResponse {

    private MockContractResponse() {

    }

    public static ContractResponse simpleContract() {
        return new ContractResponse.Builder()
                .id(1L)
                .amount(50000)
                .term("Simple term")
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .signedDate(DateTime.now())
                .build();
    }

    public static ContractResponse simpleContractWithOrganization() {
        return new ContractResponse.Builder()
                .id(1L)
                .amount(10000)
                .term("Simple term")
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .signedDate(DateTime.now())
                .organization(new OrganizationResponse.Builder()
                        .id(1L)
                        .build())
                .build();
    }

    public static List<ContractResponse> simpleContracts() {
        return Lists.newArrayList(simpleContract());
    }
}
