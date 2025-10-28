package com.foxsteven.automation_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { QuartzAutoConfiguration.class })
@EnableConfigurationProperties
public class AutomationEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationEngineApplication.class, args);
	}

}
