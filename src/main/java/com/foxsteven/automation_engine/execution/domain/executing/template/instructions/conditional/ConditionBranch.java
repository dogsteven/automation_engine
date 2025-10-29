package com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional;

import com.foxsteven.automation_engine.execution.domain.executing.template.instructions.conditional.condition.ConditionEvaluator;

import java.util.List;

public record ConditionBranch(String name,
                              List<ConditionSegment> segments, ConjunctOperator operator,
                              String nextInstructionId) {
    public boolean evaluate(ConditionEvaluator evaluator) {
        return switch (operator) {
            case OR -> segments.stream()
                    .anyMatch(evaluatee -> evaluatee.evaluate(evaluator));

            case AND -> segments.stream()
                    .allMatch(evaluatee -> evaluatee.evaluate(evaluator));
        };
    }
}
