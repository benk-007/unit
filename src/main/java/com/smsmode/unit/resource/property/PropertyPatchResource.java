package com.smsmode.unit.resource.property;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.PropertyTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import lombok.Data;

@Data
public class PropertyPatchResource {
    private String name;
    private PropertyTypeEnum type;
    private AddressEmbeddable address;
    private ContactEmbeddable contact;
    private String timezone;
    private String currency;
    private UnitTypeEnum defaultUnitType;
}
