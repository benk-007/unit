package com.smsmode.unit.resource.property;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.enumeration.PropertyTypeEnum;
import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.resource.common.AuditGetResource;
import lombok.Data;

@Data
public class PropertyGetResource {
    private String id;
    private String name;
    private String logoId;
    private AddressEmbeddable address;
    private ContactEmbeddable contact;
    private PropertyTypeEnum type;
    private String timezone;
    private String currency;
    private UnitTypeEnum defaultUnitType;
    private AuditGetResource audit;
}
