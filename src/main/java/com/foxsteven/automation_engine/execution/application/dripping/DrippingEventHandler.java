package com.foxsteven.automation_engine.execution.application.dripping;

import com.foxsteven.automation_engine.execution.domain.executing.instance.events.ExecutionSuspendedForDripping;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DrippingEventHandler {
    private final DrippingCommandHandler drippingCommandHandler;

    public DrippingEventHandler(DrippingCommandHandler drippingCommandHandler) {
        this.drippingCommandHandler = drippingCommandHandler;
    }

    @EventListener
    public void createDrippingSlotOnExecutionSuspendedForDripping(ExecutionSuspendedForDripping event) {
        drippingCommandHandler.createDrippingSlot(event.getDripperId(), event.getInstanceId());
    }
}
