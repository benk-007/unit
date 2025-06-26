/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.enumeration.AmenityEnum;
import com.smsmode.unit.enumeration.FloorSizeUnitEnum;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.enumeration.converter.AmenityEnumSetToStringConverter;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_UNIT")
public class UnitModel extends AbstractBaseModel {
    private String name;
    private String subtitle;
    @Embedded
    private AddressEmbeddable address;
    @Embedded
    private ContactEmbeddable contact;
    private boolean readiness = false;
    private String calendarColor;
    @Enumerated(EnumType.STRING)
    private UnitNatureEnum nature = UnitNatureEnum.SINGLE;
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum type;
    private String description;
    private Integer travellerAge;
    private boolean childrenAllowed = true;
    private boolean eventsAllowed = false;
    private boolean smokingAllowed = false;
    private boolean petsAllowed = false;
    private Double floorSize;
    @Enumerated(EnumType.STRING)
    private FloorSizeUnitEnum floorSizeUnit = FloorSizeUnitEnum.SQM;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MIN_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MIN_CHILDREN")),
            @AttributeOverride(name = "infants",
                    column = @Column(name = "MIN_INFANTS"))
    })
    private OccupancyEmbeddable minOccupancy = new OccupancyEmbeddable();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MAX_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MAX_CHILDREN")),
            @AttributeOverride(name = "infants",
                    column = @Column(name = "MAX_INFANTS"))
    })
    private OccupancyEmbeddable maxOccupancy = new OccupancyEmbeddable();

    @Convert(converter = AmenityEnumSetToStringConverter.class)
    private Set<AmenityEnum> amenities;

    @Embedded
    private RateEmbeddable defaultRate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "X_UNIT_RATE", joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "RATE_ID"))
    private Set<RateModel> rateTables = new HashSet<>();
}
