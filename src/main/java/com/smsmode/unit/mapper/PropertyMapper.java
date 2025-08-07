package com.smsmode.unit.mapper;

import com.smsmode.unit.model.PropertyModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.property.PropertyGetResource;
import com.smsmode.unit.resource.property.PropertyPostResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.util.ObjectUtils;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PropertyMapper {

    public abstract PropertyModel postToModel(PropertyPostResource resource);

    @Mapping(target = "logoId", source = "logoId.uuid")
    public abstract PropertyGetResource modelToGetResource(PropertyModel model);


    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    public void afterModelToItemGetResource(PropertyModel documentModel, @MappingTarget PropertyGetResource propertyGetResource) {
        propertyGetResource.setAudit(this.modelToAuditResource(documentModel));
    }
}
