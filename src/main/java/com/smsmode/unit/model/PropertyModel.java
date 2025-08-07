/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.embeddable.MediaRefEmbeddable;
import com.smsmode.unit.enumeration.PropertyTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a property (e.g., a hotel) that contains units
 *
 * Created on 07 Aug 2025 by hamzahabchi
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "X_PROPERTY")
public class PropertyModel extends AbstractBaseModel {

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(name = "uuid", column = @Column(name = "LOGO_ID"))
    private MediaRefEmbeddable logoId;

    @Embedded
    private AddressEmbeddable address;

    @Embedded
    private ContactEmbeddable contact;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyTypeEnum type = PropertyTypeEnum.HOTEL;

    @Column(nullable = false)
    private String timezone;

    @Column(nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEFAULT_UNIT_TYPE", nullable = false)
    private UnitTypeEnum defaultUnitType;
}
