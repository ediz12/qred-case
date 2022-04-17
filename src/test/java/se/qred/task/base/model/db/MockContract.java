package se.qred.task.base.model.db;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import se.qred.task.db.dto.Contract;

import java.util.List;

public class MockContract {

    private MockContract() {

    }

    public static Contract getSimpleContract() {
        return new Contract.Builder()
                .id(1L)
                .userId(1L)
                .amount(10000)
                .term("Simple term")
                .signedDate(new DateTime())
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .organization(MockOrganization.getSimpleOrganization())
                .build();
    }

    public static Contract getCreatedContract() {
        return new Contract.Builder()
                .userId(1L)
                .amount(10000)
                .term("Simple term")
                .signedDate(new DateTime())
                .interest(0.03)
                .totalAmount(10000.03)
                .totalCommission(10000.0)
                .organization(MockOrganization.getSimpleOrganization())
                .build();
    }

    public static List<Contract> getSimpleContracts() {
        return Lists.newArrayList(getSimpleContract(), getSimpleContract());
    }
}
