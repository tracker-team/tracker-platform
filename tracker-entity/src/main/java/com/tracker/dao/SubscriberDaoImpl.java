package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Subscriber;
import com.tracker.model.RequestThreadContext;
import com.tracker.model.exceptions.TrackerException;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class SubscriberDaoImpl extends BaseDaoImpl<Subscriber, Long> implements SubscriberDao {
    @Inject
    public SubscriberDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Subscriber> findByExternalId(String externalId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Subscriber> criteriaQuery = criteriaQuery();
        Root<Subscriber> root = criteriaQuery.from(Subscriber.class);
        criteriaQuery = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("externalId"), externalId),
                        criteriaBuilder.equal(root.get("tenant"), RequestThreadContext.get().getTenant().name())
                );

        List<Subscriber> subscribers = list(criteriaQuery);
        if(subscribers == null || subscribers.size() == 0) {
            return Optional.empty();
        }
        else if(subscribers.size() > 1) {
            throw new TrackerException("More than one subscribers present for external id :: " + externalId);
        }
        return Optional.ofNullable(subscribers.get(0));
    }
}
