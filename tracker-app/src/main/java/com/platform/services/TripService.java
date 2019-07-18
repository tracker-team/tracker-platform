package com.platform.services;

import com.tracker.entities.Trip;

public interface TripService {
    public Trip getTrip(String externalId);
}
