package com.foxsteven.automation_engine.execution.domain.executing.instance;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Embeddable
public class ExecutionData {
    @Column(name = "variables", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> variables;

    public ExecutionData() {}

    public ExecutionData(Map<String, Object> initialVariables) {
        this.variables = new HashMap<>(initialVariables.size());
        this.variables.putAll(initialVariables);
    }

    public boolean checkVariableExists(String name) {
        return variables.containsKey(name);
    }

    public Object readVariable(String name) {
        return variables.get(name);
    }

    public void writeVariable(String name, Object value) {
        variables.put(name, value);
    }

    public void writeVariables(Map<String, Object> nameValueMap) {
        variables.putAll(nameValueMap);
    }

    public void removeVariable(String name) {
        variables.remove(name);
    }
}
