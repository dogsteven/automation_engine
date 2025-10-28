package com.foxsteven.automation_engine.execution.domain.dripping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "execution_dripper")
public class ExecutionDripper {
    @Id
    @Column(name = "id")
    @Getter
    private UUID id;

    @Column(name = "template_id")
    @Getter
    private UUID templateId;

    @Column(name = "instruction_id")
    @Getter
    private String instructionId;

    @Column(name = "batch_size")
    @Getter
    @Setter
    private int batchSize;

    @Column(name = "dripping_interval")
    @Getter
    @Setter
    private long drippingInterval;

    public ExecutionDripper() {}

    public ExecutionDripper(UUID id, UUID templateId, String instructionId, int batchSize, long drippingInterval) {
        this.id = id;
        this.templateId = templateId;
        this.instructionId = instructionId;
        this.batchSize = batchSize;
        this.drippingInterval = drippingInterval;
    }
}
