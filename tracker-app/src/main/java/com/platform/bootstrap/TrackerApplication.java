package com.platform.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.platform.resource.SampleResource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

@Slf4j
public class TrackerApplication extends Application<TrackerConfiguration>{
    private Injector injector;
    private MetricRegistry metricRegistry;

    public static void main(String[] args) throws Exception {
        new TrackerApplication().run(args);
    }

    @Override
    public void run(TrackerConfiguration configuration, Environment environment) throws Exception {
            final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
            environment.jersey().register(SampleResource.class);
            reporter.start();
            log.info("Tracker Application is up!!");
    }

    @Override
    public void initialize(Bootstrap<TrackerConfiguration> bootstrap) {
        metricRegistry = bootstrap.getMetricRegistry();
        ObjectMapper objectMapper = bootstrap.getObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleModule simpleModule = new SimpleModule();
        objectMapper.registerModule(simpleModule);
        objectMapper.setTimeZone(TimeZone.getDefault());

        ImmutableList<Class<?>> entityClasses = ScanningHibernateBundle.findEntityClassesFromDirectory(new String[]{"com.tracker.entities"});
        HibernateBundle<TrackerConfiguration> hibernateBundle = new HibernateBundle<TrackerConfiguration>(entityClasses.get(0),
                entityClasses.subList(1, entityClasses.size() - 1).toArray(new Class<?>[]{})) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(TrackerConfiguration trackerConfiguration) {
                return trackerConfiguration.getTrackerMasterDataSource();
            }

            @Override
            protected String name() {
                return "hibernate.trackerMaster";
            }
        };
        bootstrap.addBundle(hibernateBundle) ;

        GuiceBundle<TrackerConfiguration> guiceBundle=GuiceBundle.<TrackerConfiguration>newBuilder()
                .addModule(new TrackerModule(hibernateBundle,objectMapper))
                .setConfigClass(TrackerConfiguration.class).build();
        bootstrap.addBundle(guiceBundle);

 /*       bootstrap.addBundle(new SwaggerBundle<TrackerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(TrackerConfiguration configuration) {
                return configuration.getSwagger();
            }
        });*/
    }


}
