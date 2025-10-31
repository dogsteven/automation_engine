package com.foxsteven.automation_engine.common.infrastructure.quartz;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

public class SchedulerCreationTemplate {
    private final Properties defaultSchedulerProperties;

    private final ApplicationContext applicationContext;

    private final DataSource dataSource;

    private final PlatformTransactionManager transactionManager;

    private final JobFactory jobFactory;

    public SchedulerCreationTemplate(
            Properties defaultSchedulerProperties,
            ApplicationContext applicationContext,
            DataSource dataSource,
            PlatformTransactionManager transactionManager,
            JobFactory jobFactory) {
        this.defaultSchedulerProperties = defaultSchedulerProperties;
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        this.jobFactory = jobFactory;
    }

    public Scheduler create(String name, Integer threadCount, Integer threadPriority) {
        final var factory = new SchedulerFactoryBean();
        factory.setApplicationContext(applicationContext);
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setJobFactory(jobFactory);
        factory.setSchedulerName(name);
        factory.setAutoStartup(false);

        final var properties = new Properties();

        properties.putAll(defaultSchedulerProperties);

        if (threadCount != null) {
            properties.put("org.quartz.threadPool.threadCount", threadCount.toString());
        }

        if (threadPriority != null) {
            properties.put("org.quartz.threadPool.threadPriority", threadPriority.toString());
        }

        factory.setQuartzProperties(properties);

        try {
            factory.afterPropertiesSet();

            return factory.getScheduler();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}