package com.tracker.dao;

import com.tracker.entities.Subscriber;

import java.util.Optional;

public interface SubscriberDao extends BaseDao<Subscriber, Long> {
    public Optional<Subscriber> findByExternalId(String externalId);
}
