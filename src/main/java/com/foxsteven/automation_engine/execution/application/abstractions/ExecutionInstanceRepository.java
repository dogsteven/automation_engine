package com.foxsteven.automation_engine.execution.application.abstractions;

import com.foxsteven.automation_engine.execution.domain.instance.ExecutionInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionInstanceRepository extends JpaRepository<ExecutionInstance, UUID> {
}
