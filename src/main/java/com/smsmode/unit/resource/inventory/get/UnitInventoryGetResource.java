package com.smsmode.unit.resource.inventory.get;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.inventory.NightRatesResource;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UnitInventoryGetResource {
    private String id;
    private String name;
    private String description;
    private UnitNatureEnum nature;
    private AvailabilityGetResource availability;
    private Double floorSize;
    private FloorSizeUnitEnum floorSizeUnit;
    private OccupancyEmbeddable maxOccupancy;
    private Set<AmenityEnum> amenities;
    private boolean childrenAllowed = true;
    private boolean eventsAllowed = false;
    private boolean smokingAllowed = false;
    private boolean petsAllowed = false;


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

}
