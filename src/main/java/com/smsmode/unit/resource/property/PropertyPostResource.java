package com.smsmode.unit.resource.property;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.PropertyTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyPostResource {

    @NotBlank
    private String name;
    @NotNull
    private PropertyTypeEnum type;

    private AddressEmbeddable address;
    private ContactEmbeddable contact;
    private String timezone;
    private String currency;
    private UnitTypeEnum defaultUnitType;

}

