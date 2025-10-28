package com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition;

public record VariableStringEqualityCondition(String name, String value) implements Condition {
    @Override
    public boolean evaluate(ConditionEvaluator evaluator) {
        return evaluator.evaluateVariableStringEqualityCondition(this);
    }
}
