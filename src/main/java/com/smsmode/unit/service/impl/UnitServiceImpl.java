/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.SubUnitResource;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import com.smsmode.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitDaoService unitDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<UnitItemGetResource> create(UnitPostResource unitPostResource) {

        UnitModel unitModel = unitMapper.postResourceToModel(unitPostResource);
        unitModel = unitDaoService.save(unitModel);

        if(unitModel.getNature() == UnitNatureEnum.MULTI_UNIT && unitPostResource.getSubUnits() != null) {
            for (SubUnitResource subUnit : unitPostResource.getSubUnits()) {

                if (subUnit.getUnitId() == null && (subUnit.getName() == null || subUnit.getName().isBlank())) {
                    log.warn("Skipping sub-unit creation: both unitId and name are null or blank.");
                    continue;
                }

                UnitModel childUnit;
                if (subUnit.getUnitId() != null){
                    childUnit = unitDaoService.findById(subUnit.getUnitId());
                    log.debug("Attaching existing unit [{}] to parent [{}]", subUnit.getUnitId(), unitModel.getId());
                } else {
                    childUnit = new UnitModel();
                    childUnit.setAddress(unitModel.getAddress());
                    childUnit.setContact(unitModel.getContact());
                    childUnit.setNature(UnitNatureEnum.SINGLE);
                    childUnit.setName(subUnit.getName());
                    childUnit.setReadiness(Boolean.TRUE.equals(subUnit.getReadiness()));
                    log.debug("Creating new sub-unit: {}", subUnit.getName());
                }

                childUnit.setParentUnit(unitModel);
                unitDaoService.save(childUnit);
            }
        }

        return ResponseEntity.created(URI.create("")).body(unitMapper.modelToItemGetResource(unitModel));
    }

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> retrieveAllByPage(String search, Pageable pageable) {

        Page<UnitModel> units = unitDaoService.findAllBy(null, pageable);

        return ResponseEntity.ok(units.map(unitMapper::modelToItemGetResource));
    }

    @Override
    public ResponseEntity<UnitGetResource> retrieveById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToGetResource(unit));
    }
}
