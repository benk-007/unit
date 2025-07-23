package com.smsmode.unit.resource.inventory;

import com.smsmode.unit.enumeration.BedTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class InventoryGetResource {
    private String id;
    private String name;
    private Inventory inventory;
    private Price price;
    private List<BedResource> bedding;
    private int occupancy;   // Placeholder

    @Data
    public static class Inventory {
        private int availableCount;
        private int totalCount;
    }

    @Data
    public static class Price {
        private List<NightRatesResource> nightRates;
        private double nightlyRate;
        private double totalAmount;
        private int minStay;
        private int maxStay;
    }

    @Data
    public static class BedResource {
        private BedTypeEnum type;
        private int quantity;
    }

}
