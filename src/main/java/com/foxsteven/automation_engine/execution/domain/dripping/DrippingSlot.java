package com.foxsteven.automation_engine.execution.domain.dripping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "dripping_slot")
public class DrippingSlot {
    @Id
    @Column(name = "id")
    @Getter
    private UUID id;

    @Column(name = "dripper_id")
    @Getter
    private UUID dripperId;

    @Column(name = "instance_id")
    @Getter
    private UUID instanceId;

    @Column(name = "timestamp")
    @Getter
    private OffsetDateTime timestamp;

    public DrippingSlot() {}

    public DrippingSlot(UUID id, UUID dripperId, UUID instanceId, OffsetDateTime timestamp) {
        this.id = id;
        this.dripperId = dripperId;
        this.instanceId = instanceId;
        this.timestamp = timestamp;
    }
}
