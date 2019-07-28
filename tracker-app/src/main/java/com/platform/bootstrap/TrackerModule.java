package com.platform.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.platform.ratelimiter.RateLimiterBuilder;
import com.tracker.core.module.TrackerCoreModule;
import com.tracker.model.module.TrackerModelModule;
import com.tracker.module.EntityModule;
import io.dropwizard.hibernate.HibernateBundle;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.hibernate.SessionFactory;

import java.util.HashMap;

public class TrackerModule extends AbstractModule{
    private final ObjectMapper objectMapper;
    private final MetricRegistry metricRegistry;
    private final HibernateBundle<TrackerConfiguration> hibernateBundle;
    public TrackerModule(HibernateBundle<TrackerConfiguration> hibernateBundle, ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        this.objectMapper=objectMapper;
        this.hibernateBundle=hibernateBundle;
        this.metricRegistry = metricRegistry;
    }

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
        bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
        bind(MetricRegistry.class).toInstance(metricRegistry);
        install(new EntityModule());
        install(new TrackerCoreModule());
        install(new TrackerModelModule());
    }

    @Provides
    public RateLimiterBuilder rateLimiterConfigInitializer(TrackerConfiguration imsConfiguration ) {
        HashMap<String, RateLimiterConfig> rateLimiterConfig = Maps.newHashMap();
        RateLimiterBuilder rateLimiterBuilder = new RateLimiterBuilder();
        rateLimiterBuilder.addConfigs(rateLimiterConfig);
        return rateLimiterBuilder;
    }
}
