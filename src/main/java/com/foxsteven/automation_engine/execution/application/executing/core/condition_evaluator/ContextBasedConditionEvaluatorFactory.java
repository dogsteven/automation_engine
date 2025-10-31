package com.foxsteven.automation_engine.execution.application.executing.core.condition_evaluator;

import com.foxsteven.automation_engine.execution.domain.executing.abstractions.ConditionEvaluatorFactory;
import com.foxsteven.automation_engine.execution.domain.executing.context.ConditionEvaluationContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.condition.ConditionEvaluator;
import org.springframework.stereotype.Component;

@Component
public class ContextBasedConditionEvaluatorFactory implements ConditionEvaluatorFactory {
    @Override
    public ConditionEvaluator create(ConditionEvaluationContext context) {
        return new ContextBasedConditionEvaluator(context);
    }
}
