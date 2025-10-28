package com.foxsteven.automation_engine.execution.domain.executing.context;

import com.foxsteven.automation_engine.execution.domain.template.instructions.Instruction;

import java.util.UUID;

public interface ReadInstructionExecutionContext {
    UUID instanceId();

    Instruction instruction();

    boolean checkVariableExists(String name);

    Object readVariable(String name);
}
