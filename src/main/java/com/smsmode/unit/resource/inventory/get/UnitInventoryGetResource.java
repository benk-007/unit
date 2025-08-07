package com.smsmode.unit.resource.inventory.get;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.pricing.UnitBookingRateGetResource;
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
    private UnitBookingRateGetResource rate;
}
