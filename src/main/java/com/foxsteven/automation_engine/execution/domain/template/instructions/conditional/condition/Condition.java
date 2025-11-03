package com.foxsteven.automation_engine.execution.domain.template.instructions.conditional.condition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "__TYPE__"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "VARIABLE_STRING_EQUALITY", value = VariableStringEqualityCondition.class)
})
public interface Condition {
    boolean evaluate(ConditionEvaluator evaluator);
}
