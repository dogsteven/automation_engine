package com.foxsteven.automation_engine.execution.domain.executing;

import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ActivityHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ConditionEvaluatorFactory;
import com.foxsteven.automation_engine.execution.domain.executing.abstractions.SignalDescriptionHandlerFactory;
import com.foxsteven.automation_engine.execution.domain.instance.ExecutionInstance;
import org.springframework.stereotype.Component;

@Component
public class InstructionExecutorFactory {
    private final ActionHandlerFactory actionHandlerFactory;

    private final ActivityHandlerFactory activityHandlerFactory;

    private final ConditionEvaluatorFactory conditionEvaluatorFactory;

    private final SignalDescriptionHandlerFactory signalDescriptionHandlerFactory;

    public InstructionExecutorFactory(
            ActionHandlerFactory actionHandlerFactory,
            ActivityHandlerFactory activityHandlerFactory,
            ConditionEvaluatorFactory conditionEvaluatorFactory,
            SignalDescriptionHandlerFactory signalDescriptionHandlerFactory) {
        this.actionHandlerFactory = actionHandlerFactory;
        this.activityHandlerFactory = activityHandlerFactory;
        this.conditionEvaluatorFactory = conditionEvaluatorFactory;
        this.signalDescriptionHandlerFactory = signalDescriptionHandlerFactory;
    }

    public InstructionExecutor create(ExecutionInstance instance) {
        return new InstructionExecutor(
                instance,
                actionHandlerFactory,
                activityHandlerFactory,
                conditionEvaluatorFactory,
                signalDescriptionHandlerFactory);
    }
}
