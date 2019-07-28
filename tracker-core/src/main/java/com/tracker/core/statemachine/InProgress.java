package com.tracker.core.statemachine;

import com.tracker.entities.Step;
import com.tracker.entities.TripType;
import com.tracker.model.status.StepState;
import com.tracker.model.status.StepStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InProgress extends AbstractStepState implements StepState {
    @Override
    public StepStatus getStatus() {
        return StepStatus.IN_PROGRESS;
    }

    /*
     1)get current location, if failes move to failed , we will retry later after retry we will allow manual update
     2)if manual compare with current location if reached , move to completed else waiting
     3)if auto compare with location in meta and then mark completed or waiting
    */
    @Override
    public StateMachineStatus runStep(Step step) {
        //get current location
        if(step.getTrip().getTripType().equals(TripType.MANUAL)){
            return StateMachineStatus.WAITING;
        }else {

        }
        step.setStepStatus(StepStatus.COMPLETED);
        return StateMachineStatus.SUCCESSFUL;
    }
}
