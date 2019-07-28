package com.tracker.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.tracker.dao.*;

public class EntityModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StepDao.class).to(StepDaoImpl.class).in(Singleton.class);
        bind(SubscriberDao.class).to(SubscriberDao.class).in(Singleton.class);
        bind(TripDao.class).to(TripDaoImpl.class).in(Singleton.class);
    }
}
