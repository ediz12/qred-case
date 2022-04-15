package se.qred.task.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.qred.task.core.model.enums.ApplicationStatus;
import se.qred.task.db.dto.Application;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApplicationRepository extends AbstractDAO<Application> {

    public ApplicationRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Application save(Application application) {
        return persist(application);
    }

    public Optional<Application> findById(Long id) {
        final Application application = get(id);
        if (Objects.isNull(application)) {
            return Optional.empty();
        }
        return Optional.of(application);
    }

    public List<Application> findAllInApplicationIds(List<Long> expiredApplicationIds) {
        final CriteriaQuery<Application> criteriaQuery = criteriaQuery();
        final Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.select(root)
                .where(root.get("id")
                        .in(expiredApplicationIds)
                );
        return list(criteriaQuery);
    }

    public Optional<Application> findByUserIdAndStatusIsPending(String userId) {
        final CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        final CriteriaQuery<Application> criteriaQuery = criteriaQuery();
        final Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.where(criteriaBuilder.equal(root.get("status"), ApplicationStatus.PENDING));

        final Application application = uniqueResult(currentSession().createQuery(criteriaQuery));
        if (Objects.isNull(application)) {
            return Optional.empty();
        }
        return Optional.of(application);
    }

    public Optional<Application> findByUserIdAndStatusIn(String userId, List<ApplicationStatus> applicationStatuses) {
        final CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        final CriteriaQuery<Application> criteriaQuery = criteriaQuery();
        final Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.select(root)
                .where(root.get("status")
                        .in(applicationStatuses)
                );
        final Application application = uniqueResult(currentSession().createQuery(criteriaQuery));
        if (Objects.isNull(application)) {
            return Optional.empty();
        }
        return Optional.of(application);
    }
}
