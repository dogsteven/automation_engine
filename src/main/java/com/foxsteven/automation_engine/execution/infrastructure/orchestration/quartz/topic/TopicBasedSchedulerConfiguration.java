package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.topic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "topic-schedulers")
@Component
@Getter
@Setter
public class TopicBasedSchedulerConfiguration {
    private Map<String, TopicBasedSchedulerProperties> topics = new HashMap<>();
}
