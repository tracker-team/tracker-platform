package com.tracker.core.module;

import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.tracker.core.statemachine.*;
import com.tracker.model.status.StepState;
import com.tracker.model.status.StepStatus;

import javax.inject.Named;
import java.util.Map;

public class TrackerCoreModule extends AbstractModule{
    @Override
    protected void configure() {

    }

    @Provides
    @Named("statusRegistry")
    @Inject
    public Map<StepStatus,StepState> providStepState( Injector injector){
        Map<StepStatus,StepState> stepStateMap= Maps.newHashMap();
        stepStateMap.put(StepStatus.INIT, injector.getInstance(InitState.class));
        stepStateMap.put(StepStatus.IN_PROGRESS, injector.getInstance(InProgress.class));
        stepStateMap.put(StepStatus.WAITING, injector.getInstance(Waiting.class));
        stepStateMap.put(StepStatus.COMPLETED, injector.getInstance(Completed.class));
        stepStateMap.put(StepStatus.FAILED, injector.getInstance(Failed.class));
        return stepStateMap;
    }
}
