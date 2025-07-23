package com.smsmode.unit.resource.inventory;

import lombok.Data;

import java.util.List;

@Data
public class InventoryPostResource {
    private String checkinDate;
    private String checkoutDate;
    private GuestsResource guests;
    private String segmentId;
    private String subSegmentId;
}
