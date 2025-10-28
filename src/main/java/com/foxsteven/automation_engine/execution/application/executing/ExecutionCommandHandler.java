package com.foxsteven.automation_engine.execution.application.executing;

import com.foxsteven.automation_engine.execution.application.abstractions.ExecutionInstanceRepository;
import com.foxsteven.automation_engine.execution.application.abstractions.ExecutionTemplateRepository;
import com.foxsteven.automation_engine.execution.application.exceptions.ExecutionInstanceNotFoundException;
import com.foxsteven.automation_engine.execution.domain.executing.InstructionExecutorFactory;
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

    public ExecutionCommandHandler(ExecutionTemplateRepository templateRepository,
                                   ExecutionInstanceRepository instanceRepository,
                                   InstructionExecutorFactory instructionExecutorFactory) {
        this.templateRepository = templateRepository;
        this.instanceRepository = instanceRepository;
        this.instructionExecutorFactory = instructionExecutorFactory;
    }

    @Transactional
    public UUID startExecution(UUID templateId, Map<String, Object> initialVariables) {
        final var workflow = templateRepository.findById(templateId).orElse(null);

        if (workflow == null) {
            throw new ExecutionInstanceNotFoundException(templateId);
        }

        final var id = UUID.randomUUID();
        final var data = new ExecutionData(initialVariables);
        final var instance = new ExecutionInstance(id, workflow, data);

        instanceRepository.saveAndFlush(instance);

        return id;
    }

    @Transactional
    public void executeCurrentInstruction(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        final var workflow = templateRepository.findById(instance.getTemplateId()).orElse(null);

        if (workflow == null) {
            throw new ExecutionInstanceNotFoundException(instance.getTemplateId());
        }

        final var currentInstructionId = instance.getCurrentInstructionId();

        if (currentInstructionId == null) {
            instance.forceComplete("No instruction available to execute");

            instanceRepository.saveAndFlush(instance);
            return;
        }

        final var currentInstruction = workflow.findInstructionById(currentInstructionId);

        if (currentInstruction == null) {
            final var reason = "Invalid instruction id: " +
                    "This error occurs due to workflow definition has changed during execution";

            instance.fail(reason);

            instanceRepository.saveAndFlush(instance);
            return;
        }

        final var instructionExecutor = instructionExecutorFactory.create(instance);

        instructionExecutor.execute(currentInstruction);

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void forceCompleteExecution(UUID instanceId, String reason) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.forceComplete(reason);

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void restartExecution(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.restart();

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void resumeExecutionOnActivityCompletion(UUID instanceId, Map<String, Object> payload) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.resumeOnActivityCompletion(payload);

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void resumeExecutionOnSignal(UUID instanceId, Map<String, Object> payload) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.resumeOnSignal(payload);

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void timeoutExecutionWaitingSignal(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.timeoutWaitingSignal();

        instanceRepository.saveAndFlush(instance);
    }

    @Transactional
    public void dripExecution(UUID instanceId) {
        final var instance = instanceRepository.findById(instanceId).orElse(null);

        if (instance == null) {
            throw new ExecutionInstanceNotFoundException(instanceId);
        }

        instance.drip();

        instanceRepository.saveAndFlush(instance);
    }
}
