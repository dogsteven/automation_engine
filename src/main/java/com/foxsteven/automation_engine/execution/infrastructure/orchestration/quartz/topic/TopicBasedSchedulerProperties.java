package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicBasedSchedulerProperties {
    private Integer partitionDegree;

    private Integer threadCount;

    private Integer threadPriority;
}
