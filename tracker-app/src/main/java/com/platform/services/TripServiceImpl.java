package com.platform.services;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.tracker.model.exceptions.TrackerException;
import com.tracker.dao.TripDao;
import com.tracker.entities.Trip;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class TripServiceImpl implements TripService {
    private final TripDao tripDao;

    @Inject
    public TripServiceImpl(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    @Override
    @Transactional
    public Trip getTrip(String externalId) {
        Optional<Trip> trip = tripDao.findByExternalId(externalId);
        if(!trip.isPresent()) {
            throw new TrackerException("Unable to find data with id :: " + externalId, Response.Status.NOT_FOUND);
        }
        return trip.get();
    }
}
