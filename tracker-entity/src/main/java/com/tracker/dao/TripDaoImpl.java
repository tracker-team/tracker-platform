package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Trip;
import com.tracker.model.RequestThreadContext;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

public class TripDaoImpl extends BaseDaoImpl<Trip, Long> implements TripDao {
    @Inject
    public TripDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Trip> findByExternalId(String externalId) {
        Criteria criteria = criteria();
        criteria.add(Restrictions.and(Restrictions.eq("externalId", externalId)));
        criteria.add(Restrictions.and(Restrictions.eq("tenant",
                RequestThreadContext.get().getTenant().name())));
        Trip trip = (Trip) criteria.uniqueResult();
        return Optional.ofNullable(trip);
    }
}
