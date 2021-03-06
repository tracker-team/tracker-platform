package com.platform.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tracker.model.exceptions.TrackerExceptionMapper;
import com.platform.filter.RequestFilter;
import com.platform.filter.ResponseFilter;
import com.platform.ratelimiter.RateLimiterFilter;
import com.platform.resource.SampleResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

@Slf4j
public class TrackerApplication extends Application<TrackerConfiguration>{
    private MetricRegistry metricRegistry;
    private ObjectMapper objectMapper;
    private HibernateBundle<TrackerConfiguration> hibernateBundle;
    public static void main(String[] args) throws Exception {
        new TrackerApplication().run(args);
    }

    @Override
    public void run(TrackerConfiguration configuration, Environment environment)  {
        final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
        Injector injector = Guice.createInjector(new TrackerModule(hibernateBundle,objectMapper,metricRegistry));
        environment.jersey().register(injector.getInstance(SampleResource.class));
        environment.jersey().register(injector.getInstance(RequestFilter.class));
        environment.jersey().register(injector.getInstance(ResponseFilter.class));
        environment.jersey().register(injector.getInstance(TrackerExceptionMapper.class));
        environment.jersey().register(injector.getInstance(RateLimiterFilter.class));
        log.info("Tracker Application is up!!");
    }

    @Override
    public void initialize(Bootstrap<TrackerConfiguration> bootstrap) {
        metricRegistry = bootstrap.getMetricRegistry();
        objectMapper = bootstrap.getObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleModule simpleModule = new SimpleModule();
        objectMapper.registerModule(simpleModule);
        objectMapper.setTimeZone(TimeZone.getDefault());
        ImmutableList<Class<?>> entityClasses = ScanningHibernateBundle.findEntityClassesFromDirectory(new String[]{"com.tracker.entities"});
        hibernateBundle = new HibernateBundle<TrackerConfiguration>(entityClasses.get(0),
                entityClasses.subList(1, entityClasses.size()).toArray(new Class<?>[]{})) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(TrackerConfiguration trackerConfiguration) {
                return trackerConfiguration.getTrackerMasterDataSource();
            }

            @Override
            protected String name() {
                return "hibernate.trackerMasterDB";
            }
        };
        bootstrap.addBundle(hibernateBundle) ;

        bootstrap.addBundle(new SwaggerBundle<TrackerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(TrackerConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }


}
