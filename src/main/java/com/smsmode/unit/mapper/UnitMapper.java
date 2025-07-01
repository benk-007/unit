/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitInfosPatchResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import com.smsmode.unit.resource.unit.infos.UnitInfosGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UnitMapper {

    @Mapping(source = "nature", target = "nature")
    public abstract UnitModel postResourceToModel(UnitPostResource unitPostResource);

    public abstract UnitItemGetResource modelToItemGetResource(UnitModel unitModel);

    @AfterMapping
    public void afterModelToItemGetResource(UnitModel unitModel, @MappingTarget UnitItemGetResource unitItemGetResource) {
        unitItemGetResource.setAudit(this.modelToAuditResource(unitModel));
    }

    public abstract UnitGetResource modelToGetResource(UnitModel unitModel);

    @AfterMapping
    public void afterModelToGetResource(UnitModel unitModel, @MappingTarget UnitGetResource unitGetResource) {
        unitGetResource.setAudit(this.modelToAuditResource(unitModel));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    public abstract UnitModel infosPatchResourceToModel(UnitInfosPatchResource unitInfosPatchResource, @MappingTarget UnitModel unit);

    public abstract UnitInfosGetResource modelToInfosGetResource(UnitModel unit);

    @AfterMapping
    public void afterModelToInfosGetResource(UnitModel unit, @MappingTarget UnitInfosGetResource unitInfosGetResource) {
        unitInfosGetResource.setAudit(this.modelToAuditResource(unit));
    }

    public abstract UnitDetailsGetResource modelToDetailsGetResource(UnitModel unit);

    public abstract UnitModel detailsPatchResourceToModel(UnitDetailsPatchResource unitDetailsPatchResource, @MappingTarget UnitModel unit);

/*    @Mapping(target = "unit", ignore = true)
    public abstract RoomModel roomPatchResourceToModel(RoomPatchResource room);

    @Mapping(target = "unit", ignore = true)
    public abstract RoomModel roomPatchResourceToModel(RoomPatchResource room, @MappingTarget RoomModel roomModel);*/
}
