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
import org.springframework.beans.factory.annotation.Autowired;

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

    @Mapping(source = "nature", target = "nature")
    @Mapping(source = "parentUnit.id", target = "parentUnit")
    @Mapping(source = "priority", target = "priority")
    public abstract UnitItemGetResource modelToItemGetResource(UnitModel unitModel);

    protected com.smsmode.unit.dao.service.UnitDaoService unitDaoService;

    @Autowired
    public void setUnitDaoService(com.smsmode.unit.dao.service.UnitDaoService unitDaoService) {
        this.unitDaoService = unitDaoService;
    }


    @AfterMapping
    public void afterModelToItemGetResource(UnitModel unitModel, @MappingTarget UnitItemGetResource unitItemGetResource) {
        unitItemGetResource.setAudit(this.modelToAuditResource(unitModel));

        if (unitModel.getNature() == com.smsmode.unit.enumeration.UnitNatureEnum.MULTI_UNIT) {
            var subUnits = unitDaoService.findByParentUnit(unitModel);
            var subUnitResources = subUnits.stream()
                    .map(this::modelToItemGetResource)
                    .toList();
            unitItemGetResource.setSubUnits(subUnitResources);
        }
    }

    @Mapping(source = "parentUnit.id", target = "parentUnit")
    @Mapping(source = "nature", target = "nature")
    public abstract UnitGetResource modelToGetResource(UnitModel unitModel);

    @AfterMapping
    public void afterModelToGetResource(UnitModel unitModel, @MappingTarget UnitGetResource unitGetResource) {
        unitGetResource.setAudit(this.modelToAuditResource(unitModel));
    }

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    public abstract UnitModel infosPatchResourceToModel(UnitInfosPatchResource unitInfosPatchResource, @MappingTarget UnitModel unit);

    @Mapping(source = "parentUnit.id", target = "parentUnit")
    @Mapping(source="nature", target= "nature")
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
