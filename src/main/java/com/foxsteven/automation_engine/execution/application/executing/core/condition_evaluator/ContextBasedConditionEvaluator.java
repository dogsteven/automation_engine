package com.foxsteven.automation_engine.execution.application.executing.core.condition_evaluator;

import com.foxsteven.automation_engine.execution.domain.executor.context.ConditionEvaluationContext;
import com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition.ConditionEvaluator;
import com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition.VariableStringEqualityCondition;

public class ContextBasedConditionEvaluator implements ConditionEvaluator {
    private final ConditionEvaluationContext context;

    public ContextBasedConditionEvaluator(ConditionEvaluationContext context) {
        this.context = context;
    }

    @Override
    public boolean evaluateVariableStringEqualityCondition(VariableStringEqualityCondition condition) {
        if (!context.checkVariableExists(condition.name())) {
            return false;
        }

        final var value = context.readVariable(condition.name());

        if (value == null) {
            return condition.value() == null;
        }

        return value.equals(condition.value());
    }
}
