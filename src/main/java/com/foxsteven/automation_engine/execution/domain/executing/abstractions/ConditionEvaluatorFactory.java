package com.foxsteven.automation_engine.execution.domain.executing.abstractions;

import com.foxsteven.automation_engine.execution.domain.executing.context.ReadInstructionExecutionContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition.ConditionEvaluator;

public interface ConditionEvaluatorFactory {
    ConditionEvaluator create(ReadInstructionExecutionContext context);
}
