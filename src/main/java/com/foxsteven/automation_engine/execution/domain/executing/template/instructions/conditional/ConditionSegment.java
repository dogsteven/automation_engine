package com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional;

import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.condition.Condition;
import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.condition.ConditionEvaluator;

import java.util.List;

public record ConditionSegment(List<Condition> conditions, ConjunctOperator operator) {
    public boolean evaluate(ConditionEvaluator evaluator) {
        return switch (operator) {
            case OR -> conditions.stream()
                    .anyMatch(evaluatee -> evaluatee.evaluate(evaluator));

            case AND -> conditions.stream()
                    .allMatch(evaluatee -> evaluatee.evaluate(evaluator));
        };
    }
}
