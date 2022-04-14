package se.qred.task.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.qred.task.db.dto.Organization;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Objects;
import java.util.Optional;

public class OrganizationRepository extends AbstractDAO<Organization> {

    public OrganizationRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Organization save(Organization organization) {
        return persist(organization);
    }

    public Optional<Organization> findById(Long id) {
        final Organization organization = get(id);
        if (Objects.isNull(organization)) {
            return Optional.empty();
        }
        return Optional.of(organization);
    }

    public Optional<Organization> findByOrganizationNumber(String organizationNumber){
        final CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        final CriteriaQuery<Organization> criteriaQuery = criteriaQuery();
        final Root<Organization> root = criteriaQuery.from(Organization.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("organizationNumber"), organizationNumber));
        final Organization organization = uniqueResult(currentSession().createQuery(criteriaQuery));
        if (Objects.isNull(organization)) {
            return Optional.empty();
        }
        return Optional.of(organization);
    }
}
