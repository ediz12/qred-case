package se.qred.task.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.qred.task.core.model.enums.OfferStatus;
import se.qred.task.db.dto.Offer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OfferRepository extends AbstractDAO<Offer> {

    public OfferRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Offer create(Offer offer) {
        Long id = (Long) currentSession().save(offer);
        offer.setId(id);
        return offer;
    }

    public Offer save(Offer offer) {
        return persist(offer);
    }

    public Optional<Offer> findById(Long id) {
        final Offer offer = get(id);
        if (Objects.isNull(offer)) {
            return Optional.empty();
        }
        return Optional.of(offer);
    }

    public List<Offer> findAllByStatusIsPending() {
        final CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        final CriteriaQuery<Offer> criteriaQuery = criteriaQuery();
        final Root<Offer> root = criteriaQuery.from(Offer.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("offerStatus"), OfferStatus.PENDING));
        return list(criteriaQuery);
    }
}
