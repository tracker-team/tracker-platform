package com.platform.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.platform.filter.RequestFilter;
import com.platform.filter.ResponceFilter;
import com.tracker.module.EntityModule;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class TrackerModule extends AbstractModule{
    private final ObjectMapper objectMapper;
    private final HibernateBundle<TrackerConfiguration> hibernateBundle;
    public TrackerModule(HibernateBundle<TrackerConfiguration> hibernateBundle, ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
        this.hibernateBundle=hibernateBundle;
    }

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
        bind(ResponceFilter.class).in(Singleton.class);
        bind(RequestFilter.class).in(Singleton.class);
        bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
        install(new EntityModule());

    }
}
