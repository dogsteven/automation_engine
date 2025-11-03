package com.foxsteven.automation_engine.execution.domain.executor.abstractions;

import com.foxsteven.automation_engine.execution.domain.executor.context.ConditionEvaluationContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition.ConditionEvaluator;

public interface ConditionEvaluatorFactory {
    ConditionEvaluator create(ConditionEvaluationContext context);
}
