package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.topic;

import com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz.SchedulerCreationTemplate;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TopicBasedSchedulerProvider {
    private final SchedulerCreationTemplate schedulerCreationTemplate;

    private final Map<String, TopicBasedSchedulerProperties> topicBasedSchedulerPropertiesMap;

    private final Scheduler defaultScheduler;

    private final Map<SchedulerKey, Scheduler> schedulerMap;

    private final Map<String, AtomicLong> indexMap;

    public TopicBasedSchedulerProvider(
            SchedulerCreationTemplate schedulerCreationTemplate,
            TopicBasedSchedulerConfiguration topicBasedSchedulerConfiguration,
            @Qualifier("Default")
            Scheduler defaultScheduler) {
        this.schedulerCreationTemplate = schedulerCreationTemplate;
        this.topicBasedSchedulerPropertiesMap = topicBasedSchedulerConfiguration.getTopics();
        this.defaultScheduler = defaultScheduler;
        this.schedulerMap = new HashMap<>();
        this.indexMap = new HashMap<>();

        for (final var group: this.topicBasedSchedulerPropertiesMap.keySet()) {
            this.indexMap.put(group, new AtomicLong());
        }
    }

    public Scheduler provide(String group) {
        final var partitionedSchedulerProperties = topicBasedSchedulerPropertiesMap.get(group);
        final var index = indexMap.get(group);

        if (partitionedSchedulerProperties == null || index == null) {
            return defaultScheduler;
        }

        // If the server doesn't crash, then this may fail after 300 years
        final var modularIndex = (int) (index.getAndIncrement() % partitionedSchedulerProperties.getPartitionDegree());

        final var key = new SchedulerKey(modularIndex, group);

        return schedulerMap.getOrDefault(key, defaultScheduler);
    }

    @PostConstruct
    public void createSchedulers() {
        for (final var entry: topicBasedSchedulerPropertiesMap.entrySet()) {
            final var group = entry.getKey();
            final var properties = entry.getValue();

            for (var index = 0; index < properties.getPartitionDegree(); ++index) {
                final var key = new SchedulerKey(index, group);

                final var scheduler = schedulerCreationTemplate.create(
                        String.format("%s-%d", group, index),
                        properties.getThreadCount(),
                        properties.getThreadPriority()
                );

                schedulerMap.put(key, scheduler);
            }
        }
    }

    @PreDestroy
    public void shutdownAllSchedulers() {
        for (final var scheduler: schedulerMap.values()) {
            try {
                scheduler.shutdown(true);
            } catch (SchedulerException ignored) {}
        }
    }

    public record SchedulerKey(int index, String group) {}
}
