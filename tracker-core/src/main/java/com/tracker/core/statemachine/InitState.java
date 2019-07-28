package com.tracker.core.statemachine;

import com.tracker.entities.Step;
import com.tracker.model.status.StepState;
import com.tracker.model.status.StepStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitState extends AbstractStepState implements StepState {
    @Override
    public StepStatus getStatus() {
        return StepStatus.INIT;
    }

    @Override
    public StateMachineStatus runStep(Step step) {
        step.getTrip().setCurrentStep(step);
        step.setStepStatus(StepStatus.IN_PROGRESS);
        return StateMachineStatus.SUCCESSFUL;
    }
}
