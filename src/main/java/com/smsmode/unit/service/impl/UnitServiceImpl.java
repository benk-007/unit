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
import com.smsmode.unit.resource.unit.*;
import com.smsmode.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

        if (unitModel.getNature() == UnitNatureEnum.MULTI_UNIT && unitPostResource.getSubUnits() != null) {
            for (SubUnitResource subUnit : unitPostResource.getSubUnits()) {
                processSubUnit(unitModel, subUnit);
            }
        }

        return ResponseEntity.created(URI.create("")).body(unitMapper.modelToItemGetResource(unitModel));
    }

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> retrieveAllByPage(String search, UnitNatureEnum nature, Boolean withParent, Pageable pageable) {
        boolean isSearchEmpty = (search == null || search.isBlank());
        boolean areAllParamsNull = (search == null && nature == null && withParent == null);

        if (!isSearchEmpty || areAllParamsNull) {
            Specification<UnitModel> searchSpec = Specification
                    .where(UnitSpecification.withNameLike(search))
                    .or(UnitSpecification.withSubtitleLike(search));

            Specification<UnitModel> spec = Specification
                    .where(searchSpec)
                    .and(UnitSpecification.withNature(nature))
                    .and(UnitSpecification.withParentFilter(false));


            Page<UnitModel> topLevelUnits = unitDaoService.findAllBy(spec, pageable);

            Page<UnitItemGetResource> resourcePage = topLevelUnits.map(unitMapper::modelToItemGetResource);

            return ResponseEntity.ok(resourcePage);
        }


        Specification<UnitModel> searchSpec = Specification
                .where(UnitSpecification.withNameLike(search))
                .or(UnitSpecification.withSubtitleLike(search));

        Specification<UnitModel> spec = Specification
                .where(searchSpec)
                .and(UnitSpecification.withNature(nature))
                .and(UnitSpecification.withParentFilter(withParent));


        Page<UnitModel> units = unitDaoService.findAllBy(spec, pageable);

        return ResponseEntity.ok(units.map(unitMapper::modelToItemGetResource));
    }


    @Override
    public ResponseEntity<UnitGetResource> retrieveById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToGetResource(unit));
    }

    @Override
    public ResponseEntity<UnitItemGetResource> addSubUnitToMultiUnit(String parentUnitId, SubUnitListResource subUnitListResource) {

        UnitModel parentUnit = unitDaoService.findById(parentUnitId);
        if (parentUnit == null || parentUnit.getNature() != UnitNatureEnum.MULTI_UNIT) {
            throw new IllegalArgumentException("Parent unit must exist and be of nature MULTI_UNIT.");
        }

        for (SubUnitResource subUnit : subUnitListResource.getSubUnits()) {
            processSubUnit(parentUnit, subUnit);
        }

        return ResponseEntity.ok(unitMapper.modelToItemGetResource(parentUnit));
    }

    @Override
    public void detachSubUnit(String subUnitId) {
        UnitModel subUnit = unitDaoService.findById(subUnitId);
        if (subUnit == null) {
            throw new IllegalArgumentException("Subunit not found");
        }

        if (subUnit.getParentUnit() == null) {
            log.warn("Subunit [{}] is already detached", subUnitId);
            return;
        }

        subUnit.setParentUnit(null);
        unitDaoService.save(subUnit);
    }

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> getSubUnitsOfMultiUnit(String parentUnitId, String search, Pageable pageable) {
        boolean isSearchEmpty = (search == null || search.isBlank());

        Specification<UnitModel> spec = UnitSpecification.withParentUnitId(parentUnitId);

        if (!isSearchEmpty) {
            Specification<UnitModel> searchSpec = Specification
                    .where(UnitSpecification.withNameLike(search))
                    .or(UnitSpecification.withSubtitleLike(search));

            spec = spec.and(searchSpec);
        }

        Page<UnitModel> subUnits = unitDaoService.findAllBy(spec, pageable);
        Page<UnitItemGetResource> resourcePage = subUnits.map(unitMapper::modelToItemGetResource);

        return ResponseEntity.ok(resourcePage);
    }

    private void processSubUnit(UnitModel parentUnit, SubUnitResource subUnit) {
        if (subUnit.getUnitId() == null && (subUnit.getName() == null || subUnit.getName().isBlank())) {
            log.warn("Skipping sub-unit: both unitId and name are null or blank.");
            return;
        }

        UnitModel childUnit;

        if (subUnit.getUnitId() != null) {
            childUnit = unitDaoService.findById(subUnit.getUnitId());
            if (childUnit == null) {
                log.warn("Skipping sub-unit attachment: unit [{}] not found.", subUnit.getUnitId());
                return;
            }
            log.debug("Attaching existing unit [{}] to parent [{}]", subUnit.getUnitId(), parentUnit.getId());
        } else {
            childUnit = new UnitModel();
            childUnit.setName(subUnit.getName());
            childUnit.setNature(UnitNatureEnum.SINGLE);
            childUnit.setReadiness(Boolean.TRUE.equals(subUnit.getReadiness()));
            childUnit.setAddress(parentUnit.getAddress());
            childUnit.setContact(parentUnit.getContact());
            log.debug("Creating new sub-unit: {}", subUnit.getName());
        }

        childUnit.setParentUnit(parentUnit);
        childUnit.setPriority(subUnit.getPriority() != null ? subUnit.getPriority() : 1);
        unitDaoService.save(childUnit);
    }

}
