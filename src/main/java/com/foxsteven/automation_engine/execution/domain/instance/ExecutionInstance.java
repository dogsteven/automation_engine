package com.foxsteven.automation_engine.execution.domain.instance;

import com.foxsteven.automation_engine.execution.domain.instance.events.*;
import com.foxsteven.automation_engine.execution.domain.instance.exceptions.InvalidExecutionStateException;
import com.foxsteven.automation_engine.execution.domain.template.ExecutionTemplate;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "execution_instance")
public class ExecutionInstance {
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

    @Column(name = "next_instruction_on_activity_completion")
    private String nextInstructionOnActivityCompletion;

    @Column(name = "next_instruction_on_signal")
    private String nextInstructionOnSignal;

    @Column(name = "next_instruction_on_timeout_waiting_signal")
    private String nextInstructionOnTimeoutWaitingSignal;

    @Column(name = "next_instruction_on_drip")
    private String nextInstructionOnDrip;

    @Transient
    private final List<Object> domainEvents = new ArrayList<>();

    public ExecutionInstance() {}

    public ExecutionInstance(UUID id, ExecutionTemplate template, ExecutionData initialData) {
        this.id = id;
        this.templateId = template.getId();
        this.currentInstructionId = template.getStartInstructionId();
        this.state = ExecutionState.RUNNING;
        this.data = initialData;

        domainEvents.add(new ExecutionStarted(id, currentInstructionId));
    }

    @DomainEvents
    public List<Object> getDomainEvents() {
        return domainEvents;
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        domainEvents.clear();
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

        domainEvents.add(new ExecutionForceCompleted(id, currentInstructionId, reason));
    }

    public void fail(String reason) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be failed");
        }

        state = ExecutionState.FAILED;
        failedReason = reason;

        domainEvents.add(new ExecutionFailed(id, currentInstructionId, reason));
    }

    public void restart() {
        if (state != ExecutionState.FAILED) {
            throw new InvalidExecutionStateException(id, "Only failed instances can be restart");
        }

        state = ExecutionState.RUNNING;
        failedReason = null;

        domainEvents.add(new ExecutionRestarted(id, currentInstructionId));
    }

    public void transition(String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be transitioned");
        }

        final var oldInstructionId = currentInstructionId;

        currentInstructionId = nextInstructionId;

        if (currentInstructionId == null) {
            state = ExecutionState.COMPLETED;

            domainEvents.add(new ExecutionCompleted(id, oldInstructionId));
        } else {
            domainEvents.add(new ExecutionAdvanced(id, oldInstructionId, nextInstructionId));
        }
    }

    public void waitForActivityCompletion(String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can wait for activity completion");
        }

        state = ExecutionState.WAITING_FOR_ACTIVITY_COMPLETION;
        nextInstructionOnActivityCompletion = nextInstructionId;
    }

    public void waitForSignal(String nextInstructionId, String nextInstructionIdOnTimeout) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can wait for signal");
        }

        state = ExecutionState.WAITING_FOR_SIGNAL;
        nextInstructionOnSignal = nextInstructionId;
        nextInstructionOnTimeoutWaitingSignal = nextInstructionIdOnTimeout;
    }

    public void suspendForDripping(String nextInstructionId) {
        if (state != ExecutionState.RUNNING) {
            throw new InvalidExecutionStateException(id, "Only running instances can be suspended for dripping");
        }

        state = ExecutionState.SUSPENDED_FOR_DRIPPING;
        nextInstructionOnDrip = nextInstructionId;

        domainEvents.add(new ExecutionSuspendedForDripping(id, templateId, currentInstructionId));
    }

    public void resumeOnActivityCompletion(Map<String, Object> payload) {
        if (state != ExecutionState.WAITING_FOR_ACTIVITY_COMPLETION) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for activity completion");
        }

        if (payload != null) {
            data.writeVariables(payload);
        }

        state = ExecutionState.RUNNING;
        transition(nextInstructionOnActivityCompletion);
        nextInstructionOnActivityCompletion = null;
    }

    public void resumeOnSignal(Map<String, Object> payload) {
        if (state != ExecutionState.WAITING_FOR_SIGNAL) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for signal");
        }

        if (payload != null) {
            data.writeVariables(payload);
        }

        state = ExecutionState.RUNNING;
        transition(nextInstructionOnSignal);
        nextInstructionOnSignal = null;
        nextInstructionOnTimeoutWaitingSignal = null;
    }

    public void timeoutWaitingSignal() {
        if (state != ExecutionState.WAITING_FOR_SIGNAL) {
            throw new InvalidExecutionStateException(id, "The instance is not waiting for signal");
        }

        state = ExecutionState.RUNNING;
        transition(nextInstructionOnTimeoutWaitingSignal);
        nextInstructionOnSignal = null;
        nextInstructionOnTimeoutWaitingSignal = null;
    }

    public void drip() {
        if (state != ExecutionState.SUSPENDED_FOR_DRIPPING) {
            throw new InvalidExecutionStateException(id, "The instance is not suspended for dripping");
        }

        state = ExecutionState.RUNNING;
        transition(nextInstructionOnDrip);
        nextInstructionOnDrip = null;
    }
}
