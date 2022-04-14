package se.qred.task.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.qred.task.db.dto.Contract;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ContractRepository extends AbstractDAO<Contract> {

    public ContractRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Contract save(Contract contract) {
        return persist(contract);
    }

    public List<Contract> findAllByUserId(String userId) {
        final CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        final CriteriaQuery<Contract> criteriaQuery = criteriaQuery();
        final Root<Contract> root = criteriaQuery.from(Contract.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), userId));
        return list(criteriaQuery);
    }
}
