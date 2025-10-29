package com.foxsteven.automation_engine.execution.domain.executing;

import com.foxsteven.automation_engine.common.abstractions.TimestampProvider;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActivityHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ConditionEvaluatorFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.SignalDescriptionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.instance.ExecutionInstance;
import org.springframework.stereotype.Component;

@Component
public class InstructionExecutorFactory {
    private final ActionHandlerFactory actionHandlerFactory;

    private final ActivityHandlerFactory activityHandlerFactory;

    private final ConditionEvaluatorFactory conditionEvaluatorFactory;

    private final SignalDescriptionHandlerFactory signalDescriptionHandlerFactory;

    private final TimestampProvider timestampProvider;

    public InstructionExecutorFactory(
            ActionHandlerFactory actionHandlerFactory,
            ActivityHandlerFactory activityHandlerFactory,
            ConditionEvaluatorFactory conditionEvaluatorFactory,
            SignalDescriptionHandlerFactory signalDescriptionHandlerFactory,
            TimestampProvider timestampProvider) {
        this.actionHandlerFactory = actionHandlerFactory;
        this.activityHandlerFactory = activityHandlerFactory;
        this.conditionEvaluatorFactory = conditionEvaluatorFactory;
        this.signalDescriptionHandlerFactory = signalDescriptionHandlerFactory;
        this.timestampProvider = timestampProvider;
    }

    public InstructionExecutor create(ExecutionInstance instance) {
        return new InstructionExecutor(
                instance,
                actionHandlerFactory,
                activityHandlerFactory,
                conditionEvaluatorFactory,
                signalDescriptionHandlerFactory,
                timestampProvider);
    }
}
