package com.smsmode.unit.resource.inventory.get;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.BedTypeEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.inventory.NightRatesResource;
import lombok.Data;

import java.util.List;

@Data
public class UnitInventoryGetResource {
    private String id;
    private String name;
    private UnitNatureEnum nature;
    private AvailabilityGetResource availability;
    private List<BedEmbeddable> beds;
    private Price price;

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
