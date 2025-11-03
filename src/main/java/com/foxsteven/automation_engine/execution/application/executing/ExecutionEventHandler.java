package com.foxsteven.automation_engine.execution.application.executing;

import com.foxsteven.automation_engine.execution.application.abstractions.ExecutionCommandScheduler;
import com.foxsteven.automation_engine.execution.domain.instance.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ExecutionEventHandler {
    private final ExecutionCommandScheduler executionCommandScheduler;

    public ExecutionEventHandler(ExecutionCommandScheduler executionCommandScheduler) {
        this.executionCommandScheduler = executionCommandScheduler;
    }

    @EventListener
    public void executeCurrentInstructionOnExecutionStarted(ExecutionStarted event) {
        executionCommandScheduler.enqueueExecuteCurrentInstruction(event.getInstanceId());
    }

    @EventListener
    public void executeCurrentInstructionOnExecutionAdvanced(ExecutionAdvanced event) {
        executionCommandScheduler.enqueueExecuteCurrentInstruction(event.getInstanceId());
    }

    @EventListener
    public void executeCurrentInstructionOnExecutionRestarted(ExecutionRestarted event) {
        executionCommandScheduler.enqueueExecuteCurrentInstruction(event.getInstanceId());
    }

    @EventListener
    public void scheduleTimeoutExecutionWaitingSignalOnExecutionSuspendedForSignal(ExecutionSuspendedForSignal event) {
        executionCommandScheduler.scheduleTimeoutExecutionSignalWaiting(
                event.getInstanceId(),
                event.getSignalToken(),
                event.getTimeoutTimestamp());
    }

    @EventListener
    public void unscheduleTimeoutExecutionWaitingSignalOnExecutionResumedOnSignal(ExecutionResumedOnSignal event) {
        executionCommandScheduler.unscheduleTimeoutExecutionSignalWaiting(
                event.getInstanceId(),
                event.getSignalToken());
    }
}
