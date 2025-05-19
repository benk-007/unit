/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.embeddable.OccupancyEmbeddable;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UnitTypeEnum type = UnitTypeEnum.SINGLE;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MIN_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MIN_CHILDREN")),
            @AttributeOverride(name = "infants",
                    column = @Column(name = "MIN_INFANTS"))
    })
    private OccupancyEmbeddable minOccupancy;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "adults",
                    column = @Column(name = "MAX_ADULTS")),
            @AttributeOverride(name = "children",
                    column = @Column(name = "MAX_CHILDREN")),
            @AttributeOverride(name = "infants",
                    column = @Column(name = "MAX_INFANTS"))
    })
    private OccupancyEmbeddable maxOccupancy;

}
