package com.tracker.core.statemachine;

import com.tracker.entities.Step;
import com.tracker.model.status.StepStatus;

public class StateMachine {

    public StateMachineStatus runStateMachine(Step step){
        StateMachineStatus stateMachineStatus=StateMachineStatus.FAILED;
        do{
            AbstractStepState stepState = (AbstractStepState) step.getStepStatus().getStepState();
            stateMachineStatus= stepState.runStep(step);
        }while(step.getStepStatus()!= StepStatus.COMPLETED
                || step.getStepStatus()!= StepStatus.WAITING
                ||step.getStepStatus()!= StepStatus.FAILED);
        return stateMachineStatus;
    }
}
