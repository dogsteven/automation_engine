package com.foxsteven.automation_engine.execution.application.executing;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import com.foxsteven.automation_engine.common.utilities.DomainEventPublisher;
import com.foxsteven.automation_engine.execution.application.abstractions.repositories.ExecutionInstanceRepository;
import com.foxsteven.automation_engine.execution.application.abstractions.repositories.ExecutionTemplateRepository;
import com.foxsteven.automation_engine.execution.application.exceptions.ExecutionInstanceNotFoundException;
import com.foxsteven.automation_engine.execution.domain.executor.InstructionExecutorFactory;
import com.foxsteven.automation_engine.execution.domain.instance.ExecutionData;
import com.foxsteven.automation_engine.execution.domain.instance.ExecutionInstance;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
public class ExecutionCommandHandler {
    private final ExecutionTemplateRepository templateRepository;

    private final ExecutionInstanceRepository instanceRepository;

    private final InstructionExecutorFactory instructionExecutorFactory;

    private final DomainEventPublisher domainEventPublisher;

    private final TimestampProvider timestampProvider;

    public ExecutionCommandHandler(ExecutionTemplateRepository templateRepository,
                                   ExecutionInstanceRepository instanceRepository,
                                   InstructionExecutorFactory instructionExecutorFactory,
                                   DomainEventPublisher domainEventPublisher,
                                   TimestampProvider timestampProvider) {
        this.templateRepository = templateRepository;
        this.instanceRepository = instanceRepository;
        this.instructionExecutorFactory = instructionExecutorFactory;
        this.domainEventPublisher = domainEventPublisher;
        this.timestampProvider = timestampProvider;
    }

    @Transactional
    public void startExecution(UUID id, UUID templateId, Map<String, Object> initialVariables) {
        final var template = templateRepository.findById(templateId).orElse(null);

        if (template == null) {
            throw new ExecutionInstanceNotFoundException(templateId);
        }

        final var data = new ExecutionData(initialVariables);
        final var instance = new ExecutionInstance(id, template, data);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void executeCurrentInstruction(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        final var template = templateRepository.findById(instance.getTemplateId()).orElse(null);

        if (template == null) {
            throw new ExecutionInstanceNotFoundException(instance.getTemplateId());
        }

        final var currentInstructionId = instance.getCurrentInstructionId();

        if (currentInstructionId == null) {
            instance.forceComplete("No instruction available to execute");

            instanceRepository.saveAndFlush(instance);
            domainEventPublisher.publishDomainEvents(instance);
            return;
        }

        final var currentInstruction = template.findInstructionById(currentInstructionId);

        if (currentInstruction == null) {
            final var reason = "Invalid instruction id: " +
                    "This error occurs due to template definition has changed during execution";

            instance.fail(reason);

            instanceRepository.saveAndFlush(instance);
            domainEventPublisher.publishDomainEvents(instance);
            return;
        }

        final var instructionExecutor = instructionExecutorFactory.create(instance);

        instructionExecutor.execute(currentInstruction);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void forceCompleteExecution(UUID instanceId, String reason) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.forceComplete(reason);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void restartExecution(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.restart();

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void resumeExecutionOnActivityCompletion(UUID instanceId, Map<String, Object> payload) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.resumeOnActivityCompletion(payload);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void resumeExecutionOnSignal(UUID instanceId, String signalToken, Map<String, Object> payload) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.resumeOnSignal(signalToken, payload);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void timeoutExecutionWaitingSignal(UUID instanceId, String signalToken) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        final var now = timestampProvider.provideOffsetDateTimeNow();

        instance.timeoutWaitingSignal(signalToken, now);

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }

    @Transactional
    public void resumeExecutionFromDripping(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.resumeOnDripping();

        instanceRepository.saveAndFlush(instance);
        domainEventPublisher.publishDomainEvents(instance);
    }
}
