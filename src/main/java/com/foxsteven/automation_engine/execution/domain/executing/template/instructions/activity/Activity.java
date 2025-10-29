package com.foxsteven.automation_engine.execution.domain.executing.template.instructions.activity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "__TYPE__"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "SLEEP", value = SleepActivity.class)
})
public interface Activity {
    void handle(ActivityHandler handler);
}
