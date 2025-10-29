package com.foxsteven.automation_engine.execution.domain.executing.instance;

import com.foxsteven.automation_engine.common.abstractions.DomainEventSource;
import com.foxsteven.automation_engine.execution.domain.executing.instance.events.*;
import com.foxsteven.automation_engine.execution.domain.executing.instance.exceptions.InvalidExecutionStateException;
import com.foxsteven.automation_engine.execution.domain.executing.template.ExecutionTemplate;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "execution_instance")
public class ExecutionInstance extends DomainEventSource {
    @Id
    @Column(name = "id")
    @Getter
    private UUID id;

    @Column(name = "template_id")
    @Getter
    private UUID templateId;

    @Column(name = "current_instruction_id")
    @Getter
    private String currentInstructionId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @Getter
    private ExecutionState state;

    @Embedded
    @Getter
    private ExecutionData data;

    @Column(name = "failed_reason")
    @Getter
    private String failedReason;

    @Column(name = "force_completed_reason")
    @Getter
    private String forceCompletedReason;

    @Column(name = "next_instruction_id_on_activity_completion")
    private String nextInstructionIdOnActivityCompletion;

    @Column(name = "next_instruction_Id_on_signal")
    private String nextInstructionIdOnSignal;

    @Column(name = "next_instruction_id_on_timeout_waiting_signal")
    private String nextInstructionIdOnTimeoutWaitingSignal;

    @Column(name = "waiting_signal_timeout_timestamp")
    @Getter
    private OffsetDateTime waitingSignalTimeoutTimestamp;

    @Column(name = "current_dripper_id")
    @Getter
    private UUID currentDripperId;

    @Column(name = "next_instruction_id_on_drip")
    private String nextInstructionIdOnDrip;

    public ExecutionInstance() {
        super();
    }

    public ExecutionInstance(UUID id, ExecutionTemplate template, ExecutionData initialData) {
        super();
        this.id = id;
        this.templateId = template.getId();
        this.currentInstructionId = template.getStartInstructionId();
        this.state = ExecutionState.RUNNING;
        this.data = initialData;

        raiseDomainEvent(new ExecutionStarted(id, currentInstructionId));
    }

    public boolean isRunning() {
        return state == ExecutionState.RUNNING;
    }

    public void forceComplete(String reason) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be force completed");
        }

        state = ExecutionState.COMPLETED;
        forceCompletedReason = reason;

        raiseDomainEvent(new ExecutionForceCompleted(id, currentInstructionId, reason));
    }

    public void fail(String reason) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be failed");
        }

        state = ExecutionState.FAILED;
        failedReason = reason;

        raiseDomainEvent(new ExecutionFailed(id, currentInstructionId, reason));
    }

    public void restart() {
        if (state != ExecutionState.FAILED) {
            throw new InvalidExecutionStateException(id, "Only failed instances can be restart");
        }

        state = ExecutionState.RUNNING;
        failedReason = null;

        raiseDomainEvent(new ExecutionRestarted(id, currentInstructionId));
    }

    public void transition(String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be transitioned");
        }

        final var oldInstructionId = currentInstructionId;

        currentInstructionId = nextInstructionId;

        if (currentInstructionId == null) {
            state = ExecutionState.COMPLETED;

            raiseDomainEvent(new ExecutionCompleted(id, oldInstructionId));
        } else {
            raiseDomainEvent(new ExecutionAdvanced(id, oldInstructionId, nextInstructionId));
        }
    }

    public void suspendForActivityCompletion(String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can wait for activity completion");
        }

        state = ExecutionState.SUSPENDED_FOR_ACTIVITY_COMPLETION;
        nextInstructionIdOnActivityCompletion = nextInstructionId;

        raiseDomainEvent(new ExecutionSuspendedForActivityCompletion(id, currentInstructionId));
    }

    public void suspendForSignal(String nextInstructionId,
                                 String nextInstructionIdOnTimeout,
                                 OffsetDateTime timeoutTimestamp) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can wait for signal");
        }

        state = ExecutionState.SUSPENDED_FOR_SIGNAL;
        nextInstructionIdOnSignal = nextInstructionId;
        nextInstructionIdOnTimeoutWaitingSignal = nextInstructionIdOnTimeout;
        waitingSignalTimeoutTimestamp = timeoutTimestamp;

        raiseDomainEvent(new ExecutionSuspendedForSignal(id, currentInstructionId, timeoutTimestamp));
    }

    public void suspendForDripping(UUID dripperId, String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be suspended for dripping");
        }

        state = ExecutionState.SUSPENDED_FOR_DRIPPING;
        currentDripperId = dripperId;
        nextInstructionIdOnDrip = nextInstructionId;

        raiseDomainEvent(new ExecutionSuspendedForDripping(id, currentInstructionId, dripperId));
    }

    public void resumeOnActivityCompletion(Map<String, Object> payload) {
        if (state != ExecutionState.SUSPENDED_FOR_ACTIVITY_COMPLETION) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for activity completion");
        }

        if (payload != null) {
            data.writeVariables(payload);
        }

        state = ExecutionState.RUNNING;

        transition(nextInstructionIdOnActivityCompletion);
        nextInstructionIdOnActivityCompletion = null;
    }

    public void resumeOnSignal(String signalToken, Map<String, Object> payload) {
        if (state != ExecutionState.SUSPENDED_FOR_SIGNAL) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for signal");
        }

        if (!currentInstructionId.equals(signalToken)) {
            throw new InvalidExecutionStateException(id, "Wrong signal token");
        }

        if (payload != null) {
            data.writeVariables(payload);
        }

        state = ExecutionState.RUNNING;
        raiseDomainEvent(new ExecutionResumedOnSignal(id, currentInstructionId));

        transition(nextInstructionIdOnSignal);
        nextInstructionIdOnSignal = null;
        nextInstructionIdOnTimeoutWaitingSignal = null;
        waitingSignalTimeoutTimestamp = null;
    }

    public void timeoutWaitingSignal(String signalToken, OffsetDateTime now) {
        if (state != ExecutionState.SUSPENDED_FOR_SIGNAL) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for signal");
        }

        if (now.isBefore(waitingSignalTimeoutTimestamp)) {
            throw new InvalidExecutionStateException(id, "The signal is not timeout yet");
        }

        if (!currentInstructionId.equals(signalToken)) {
            throw new InvalidExecutionStateException(id, "Wrong signal token");
        }

        state = ExecutionState.RUNNING;

        transition(nextInstructionIdOnTimeoutWaitingSignal);
        nextInstructionIdOnSignal = null;
        nextInstructionIdOnTimeoutWaitingSignal = null;
        waitingSignalTimeoutTimestamp = null;
    }

    public void resumeOnDripping() {
        if (state != ExecutionState.SUSPENDED_FOR_DRIPPING) {
            throw new InvalidExecutionStateException(id, "The instance is not suspended for dripping");
        }

        state = ExecutionState.RUNNING;

        transition(nextInstructionIdOnDrip);
        currentDripperId = null;
        nextInstructionIdOnDrip = null;
    }
}
