package com.tracker.model.status;

import com.google.inject.Inject;

import javax.inject.Named;
import java.util.Map;

public enum StepStatus {
    INIT,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    WAITING;

    @Inject
    @Named("statusRegistry")
    private static Map<StepStatus,StepState> stepStatusMap;

    public StepState getStepState() {
        return stepStatusMap.get(this);
    }
}
