package com.foxsteven.automation_engine.execution.domain.executing.context;


import com.foxsteven.automation_engine.execution.domain.instance.ExecutionInstance;
import com.foxsteven.automation_engine.execution.domain.template.instructions.Instruction;

import java.util.Map;
import java.util.UUID;

public class InstructionExecutionContext implements ReadWriteInstructionExecutionContext {
    private final ExecutionInstance instance;

    private final Instruction instruction;

    public InstructionExecutionContext(ExecutionInstance instance, Instruction instruction) {
        this.instance = instance;
        this.instruction = instruction;
    }

    @Override
    public UUID instanceId() {
        return instance.getId();
    }

    @Override
    public Instruction instruction() {
        return instruction;
    }

    @Override
    public boolean checkVariableExists(String name) {
        return instance.getData().checkVariableExists(name);
    }

    @Override
    public Object readVariable(String name) {
        return instance.getData().readVariable(name);
    }

    @Override
    public void writeVariable(String name, Object value) {
        instance.getData().writeVariable(name, value);
    }

    @Override
    public void writeVariables(Map<String, Object> nameValueMap) {
        instance.getData().writeVariables(nameValueMap);
    }

    @Override
    public void removeVariable(String name) {
        instance.getData().removeVariable(name);
    }
}
