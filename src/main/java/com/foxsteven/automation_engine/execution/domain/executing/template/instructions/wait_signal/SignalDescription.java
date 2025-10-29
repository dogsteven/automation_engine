package com.foxsteven.automation_engine.execution.domain.executing.template.instructions.wait_signal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "__TYPE__"
)
@JsonSubTypes({

})
public interface SignalDescription {
    void handle(SignalDescriptionHandler handler);
}
