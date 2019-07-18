package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Subscriber;
import com.tracker.model.RequestThreadContext;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

public class SubscriberDaoImpl extends BaseDaoImpl<Subscriber, Long> implements SubscriberDao {
    @Inject
    public SubscriberDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Subscriber> findByExternalId(String externalId) {
        Criteria criteria = criteria();
        criteria.add(Restrictions.and(Restrictions.eq("externalId", externalId)));
        criteria.add(Restrictions.and(Restrictions.eq("tenant",
                RequestThreadContext.get().getTenant().name())));
        Subscriber subscriber = (Subscriber) criteria.uniqueResult();
        return Optional.ofNullable(subscriber);
    }
}
