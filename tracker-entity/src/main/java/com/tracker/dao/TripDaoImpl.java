package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Trip;
import com.tracker.model.RequestThreadContext;
import com.tracker.model.exceptions.TrackerException;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class TripDaoImpl extends BaseDaoImpl<Trip, Long> implements TripDao {
    @Inject
    public TripDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Trip> findByExternalId(String externalId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = criteriaQuery();
        Root<Trip> root = criteriaQuery.from(Trip.class);
        criteriaQuery = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("externalId"), externalId),
                        criteriaBuilder.equal(root.get("tenant"), RequestThreadContext.get().getTenant().name())
                );

        List<Trip> trips = list(criteriaQuery);
        if(trips == null || trips.size() == 0) {
            return Optional.empty();
        }
        else if(trips.size() > 1) {
            throw new TrackerException("More than one trips present for external id :: " + externalId);
        }
        return Optional.ofNullable(trips.get(0));
    }
}
