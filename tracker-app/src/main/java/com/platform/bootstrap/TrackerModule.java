package com.platform.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;

public class TrackerModule extends AbstractModule{
    private final ObjectMapper objectMapper;
    public TrackerModule(ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
    }

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
    }
}
