package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryPostResource {
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private GuestsResource guests;
    private String segmentId;
    private String subSegmentId;
}
