package com.foxsteven.automation_engine.execution.domain.executor;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import com.foxsteven.automation_engine.execution.domain.executor.abstractions.ActionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executor.abstractions.ActivityHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executor.abstractions.ConditionEvaluatorFactory;
import com.foxsteven.automation_engine.execution.domain.executor.abstractions.SignalDescriptionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executor.context.StandardExecutionContext;
import com.foxsteven.automation_engine.execution.domain.executor.utilities.DiscreteDistributionSampler;
import com.foxsteven.automation_engine.execution.domain.instance.ExecutionInstance;
import com.foxsteven.automation_engine.execution.domain.instance.exceptions.InvalidExecutionStateException;
import com.foxsteven.automation_engine.execution.domain.template.instructions.*;
import com.foxsteven.automation_engine.execution.domain.template.instructions.split_branch.SplitBranch;

public class InstructionExecutor implements InstructionHandler {
    private final ExecutionInstance instance;

    private final ActionHandlerFactory actionHandlerFactory;

    private final ActivityHandlerFactory activityHandlerFactory;

    private final ConditionEvaluatorFactory conditionEvaluatorFactory;

    private final SignalDescriptionHandlerFactory signalDescriptionHandlerFactory;

    private final TimestampProvider timestampProvider;

    public InstructionExecutor(ExecutionInstance instance,
                               ActionHandlerFactory actionHandlerFactory,
                               ActivityHandlerFactory activityHandlerFactory,
                               ConditionEvaluatorFactory conditionEvaluatorFactory,
                               SignalDescriptionHandlerFactory signalDescriptionHandlerFactory,
                               TimestampProvider timestampProvider) {
        this.instance = instance;
        this.actionHandlerFactory = actionHandlerFactory;
        this.activityHandlerFactory = activityHandlerFactory;
        this.conditionEvaluatorFactory = conditionEvaluatorFactory;
        this.signalDescriptionHandlerFactory = signalDescriptionHandlerFactory;
        this.timestampProvider = timestampProvider;
    }

    public void execute(Instruction instruction) {
        if (!instance.isRunning()) {
            throw new InvalidExecutionStateException(instance.getId(), "This instance is not running");
        }

        instruction.handle(this);
    }

    private StandardExecutionContext createInstructionExecutionContext(Instruction instruction) {
        return new StandardExecutionContext(instance, instruction);
    }

    @Override
    public void handleActionInstruction(ActionInstruction instruction) {
        final var context = createInstructionExecutionContext(instruction);
        final var actionHandler = actionHandlerFactory.create(context);

        try {
            instruction.action().handle(actionHandler);
        } catch (Exception exception) {
            instance.fail(exception.getMessage());
            return;
        }

        instance.transition(instruction.nextInstructionId());
    }

    @Override
    public void handleConditionalInstruction(ConditionalInstruction instruction) {
        final var context = createInstructionExecutionContext(instruction);
        final var conditionEvaluator = conditionEvaluatorFactory.create(context);

        String nextInstructionId = null;
        boolean found = false;

        try {
            for (final var branch: instruction.branches()) {
                if (branch.evaluate(conditionEvaluator)) {
                    nextInstructionId = branch.nextInstructionId();
                    found = true;
                }
            }
        } catch (Exception exception) {
            instance.fail(exception.getMessage());
            return;
        }

        if (!found) {
            nextInstructionId = instruction.fallbackInstructionId();
        }

        instance.transition(nextInstructionId);
    }

    @Override
    public void handleGoToInstruction(GoToInstruction instruction) {
        instance.transition(instruction.nextInstructionId());
    }

    @Override
    public void handleActivityInstruction(ActivityInstruction instruction) {
        final var context = createInstructionExecutionContext(instruction);
        final var activityHandler = activityHandlerFactory.create(context);

        try {
            instruction.activity().handle(activityHandler);
        } catch (Exception exception) {
            instance.fail(exception.getMessage());
            return;
        }

        instance.suspendForActivityCompletion(instruction.nextInstructionId());
    }

    @Override
    public void handleWaitForSignalInstruction(WaitForSignalInstruction instruction) {
        final var context = createInstructionExecutionContext(instruction);
        final var signalDescriptionHandler = signalDescriptionHandlerFactory.create(context);

        try {
            instruction.signalDescription().handle(signalDescriptionHandler);
        } catch (Exception exception) {
            instance.fail(exception.getMessage());
            return;
        }

        final var timeoutTimestamp = timestampProvider.provideOffsetDateTimeNow()
                        .plusSeconds(instruction.timeoutDuration());

        instance.suspendForSignal(
                instruction.nextInstructionId(),
                instruction.nextInstructionIdOnTimeout(),
                timeoutTimestamp);
    }

    @Override
    public void handleSplitInstruction(SplitInstruction instruction) {
        final var branch = DiscreteDistributionSampler.sample(instruction.branches(), SplitBranch::weight);

        instance.transition(branch.nextInstructionId());
    }

    @Override
    public void handleDripInstruction(DripInstruction instruction) {
        instance.suspendForDripping(instruction.dripperId(), instruction.nextInstructionId());
    }
}
