package com.tracker.core.statemachine;

import com.tracker.entities.Step;
import com.tracker.model.status.StepState;

public abstract class AbstractStepState implements StepState{
    public abstract StateMachineStatus runStep(Step step);
}
