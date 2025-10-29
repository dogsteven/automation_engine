package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ConditionEvaluationContext;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.condition.ConditionEvaluator;

public interface ConditionEvaluatorFactory {
    ConditionEvaluator create(ConditionEvaluationContext context);
}
