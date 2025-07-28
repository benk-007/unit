package com.smsmode.unit.service;

import com.smsmode.unit.resource.inventory.InventoryGetResource;
import com.smsmode.unit.resource.inventory.InventoryPostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InventoryService {
    ResponseEntity<Page<InventoryGetResource>> getInventory(InventoryPostResource inventoryPostResource, Pageable pageable);
}
