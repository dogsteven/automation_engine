package com.foxsteven.automation_engine.execution.application.logging;

import com.foxsteven.automation_engine.execution.domain.instance.events.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventHandler {
    private final LoggingCommandHandler loggingCommandHandler;

    public LoggingEventHandler(LoggingCommandHandler loggingCommandHandler) {
        this.loggingCommandHandler = loggingCommandHandler;
    }

    @EventListener
    public void writeLogEntryOnExecutionStarted(ExecutionStarted event) {
        loggingCommandHandler.createLogEntryForExecutionStarted(
                event.getInstanceId(),
                event.getStartInstructionId());
    }

    @EventListener
    public void writeLogEntryOnExecutionAdvanced(ExecutionAdvanced event) {
        loggingCommandHandler.createLogEntryForExecutionAdvanced(
                event.getInstanceId(),
                event.getFromInstructionId(),
                event.getToInstructionId());
    }

    @EventListener
    public void writeLogEntryOnExecutionFailed(ExecutionFailed event) {
        loggingCommandHandler.createLogEntryForExecutionFailed(
                event.getInstanceId(),
                event.getFailedInstructionId(),
                event.getReason());
    }

    @EventListener
    public void writeLogEntryOnExecutionRestarted(ExecutionRestarted event) {
        loggingCommandHandler.createLogEntryForExecutionRestarted(
                event.getInstanceId(),
                event.getRestartInstructionId());
    }

    @EventListener
    public void writeLogEntryOnExecutionForceCompleted(ExecutionForceCompleted event) {
        loggingCommandHandler.createLogEntryForExecutionForceCompleted(
                event.getInstanceId(),
                event.getInstructionId(),
                event.getReason());
    }

    @EventListener
    public void writeLogEntryOnExecutionCompleted(ExecutionCompleted event) {
        loggingCommandHandler.createLogEntryForExecutionCompleted(
                event.getInstanceId(),
                event.getLastInstructionId());
    }

    @EventListener
    public void writeLogEntryOnExecutionSuspendedForActivityCompletion(
            ExecutionSuspendedForActivityCompletion event) {
        loggingCommandHandler.createLogEntryForExecutionSuspendedForActivityCompletion(
                event.getInstanceId(),
                event.getInstructionId());
    }

    @EventListener
    public void writeLogEntryOnExecutionSuspendedForSignal(ExecutionSuspendedForSignal event) {
        loggingCommandHandler.createLogEntryForExecutionSuspendedForSignal(
                event.getInstanceId(),
                event.getSignalToken(),
                event.getTimeoutTimestamp());
    }

    @EventListener
    public void writeLogEntryOnExecutionSuspendedForDripping(ExecutionSuspendedForDripping event) {
        loggingCommandHandler.createLogEntryForExecutionSuspendedForDripping(
                event.getInstanceId(),
                event.getInstructionId(),
                event.getDripperId());
    }
}
