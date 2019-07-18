package com.tracker.dao;

import com.tracker.entities.Trip;

import java.util.Optional;

public interface TripDao extends BaseDao<Trip, Long> {
    public Optional<Trip> findByExternalId(String externalId);
}
