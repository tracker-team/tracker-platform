package com.tracker.core.statemachine;

import com.tracker.entities.Step;
import com.tracker.model.status.StepState;
import com.tracker.model.status.StepStatus;

public class Completed extends AbstractStepState implements StepState {
    @Override
    public StateMachineStatus runStep(Step step) {
        step.setStepStatus(StepStatus.COMPLETED);
        return StateMachineStatus.SUCCESSFUL;
    }

    @Override
    public StepStatus getStatus() {
        return StepStatus.WAITING;
    }
}
