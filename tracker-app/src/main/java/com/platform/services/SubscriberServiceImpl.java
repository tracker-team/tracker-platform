package com.platform.services;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.tracker.model.exceptions.TrackerException;
import com.tracker.dao.SubscriberDao;
import com.tracker.entities.Subscriber;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberDao subscriberDao;

    @Inject
    public SubscriberServiceImpl(SubscriberDao subscriberDao) {
        this.subscriberDao = subscriberDao;
    }

    @Override
    @Transactional
    public Subscriber getSubscriber(String externalId) {
        Optional<Subscriber> subscriber = subscriberDao.findByExternalId(externalId);
        if(!subscriber.isPresent()) {
            throw new TrackerException("Unable to find subscriber with id :: " + externalId, Response.Status.NOT_FOUND);
        }
        return subscriber.get();
    }
}
