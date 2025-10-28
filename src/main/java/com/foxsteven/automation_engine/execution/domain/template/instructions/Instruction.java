package com.foxsteven.automation_engine.execution.domain.template.instructions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "__TYPE__"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "ACTION", value = ActionInstruction.class),
        @JsonSubTypes.Type(name = "CONDITIONAL", value = ConditionalInstruction.class),
        @JsonSubTypes.Type(name = "GOTO", value = GoToInstruction.class),
        @JsonSubTypes.Type(name = "ACTIVITY", value = ActivityInstruction.class),
        @JsonSubTypes.Type(name = "WAIT_FOR_SIGNAL", value = WaitForSignalInstruction.class),
        @JsonSubTypes.Type(name = "SPLIT", value = SplitInstruction.class),
        @JsonSubTypes.Type(name = "DRIP", value = DripInstruction.class)
})
public interface Instruction {
    String id();

    void handle(InstructionHandler handler);
}
