package com.foxsteven.automation_engine.execution.infrastructure.orchestration.quartz;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzSchedulerConfiguration {
    @Bean
    @Qualifier("Default")
    public Scheduler defaultScheduler(
            SchedulerCreationTemplate schedulerCreationTemplate) {
        return schedulerCreationTemplate.create("Default", null, null);
    }

    @Bean
    public SchedulerCreationTemplate schedulerCreationTemplate(
            @Qualifier("QuartzDefault")
            Properties defaultSchedulerProperties,
            ApplicationContext applicationContext,
            DataSource dataSource,
            PlatformTransactionManager transactionManager,
            JobFactory jobFactory) {
        return new SchedulerCreationTemplate(
                defaultSchedulerProperties,
                applicationContext,
                dataSource,
                transactionManager,
                jobFactory);
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }

    @Bean
    @Qualifier("QuartzDefault")
    public Properties defaultSchedulerProperties() {
        final var props = new Properties();

        // job store
        props.setProperty("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        props.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        props.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");

        // clustering
        props.setProperty("org.quartz.jobStore.isClustered", "true");
        props.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
        props.setProperty("org.quartz.jobStore.selectWithLockSQL",
                "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

        // scheduler
        props.setProperty("org.quartz.scheduler.instanceName", "DefaultScheduler");
        props.setProperty("org.quartz.scheduler.instanceId", "AUTO");

        // thread pool
        props.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        props.setProperty("org.quartz.threadPool.threadCount", "25");
        props.setProperty("org.quartz.threadPool.threadPriority", "5");

        return props;
    }
}
