package com.foxsteven.automation_engine.execution.application.abstractions.repositories;

import com.foxsteven.automation_engine.execution.domain.executing.template.ExecutionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionTemplateRepository extends JpaRepository<ExecutionTemplate, UUID> {
}
