package com.foxsteven.automation_engine.execution.application.abstractions.repositories;

import com.foxsteven.automation_engine.execution.domain.dripping.DrippingSlot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DrippingSlotRepository extends JpaRepository<DrippingSlot, UUID> {
    List<DrippingSlot> findByDripperId(UUID dripperId, Pageable pageable);
}
