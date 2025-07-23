package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.InventoryController;
import com.smsmode.unit.resource.inventory.InventoryGetResource;
import com.smsmode.unit.resource.inventory.InventoryPostResource;
import com.smsmode.unit.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryControllerImpl implements InventoryController {
    private final InventoryService inventoryService;

    @Override
    public ResponseEntity<Page<InventoryGetResource>> getAvailability(InventoryPostResource request, Pageable pageable) {
        return inventoryService.getInventory(request, pageable);
    }
}
