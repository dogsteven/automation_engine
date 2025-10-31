package com.foxsteven.automation_engine.execution.application.abstractions.repositories;

import com.foxsteven.automation_engine.execution.domain.dripping.ExecutionDripper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExecutionDripperRepository extends JpaRepository<ExecutionDripper, UUID> {
}
