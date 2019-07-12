package com.tracker.platform.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.tracker.platform.resource.SampleResource;
import io.dropwizard.Application;
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

        GuiceBundle<TrackerConfiguration> guiceBundle=GuiceBundle.<TrackerConfiguration>newBuilder()
                .addModule(new TrackerModule(objectMapper))
                .setConfigClass(TrackerConfiguration.class).build();
        bootstrap.addBundle(guiceBundle);
    }
}
