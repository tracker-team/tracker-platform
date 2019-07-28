package com.tracker.model.module;

import com.google.inject.AbstractModule;
import com.tracker.model.status.StepStatus;

public class TrackerModelModule extends AbstractModule{
    @Override
    protected void configure() {
        requestStaticInjection(StepStatus.class);
    }
}
