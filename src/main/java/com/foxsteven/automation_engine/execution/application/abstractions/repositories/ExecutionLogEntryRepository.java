package com.foxsteven.automation_engine.execution.application.abstractions.repositories;

import com.foxsteven.automation_engine.execution.domain.logging.ExecutionLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionLogEntryRepository extends JpaRepository<ExecutionLogEntry, ExecutionLogEntry.Identifier> {
}
