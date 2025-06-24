/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.mapper.RateMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import com.smsmode.unit.resource.unit.rate.DefaultRatePatchResource;
import com.smsmode.unit.service.UnitRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of UnitRateService for managing unit rates.
 * Refactored to use clean mapper API and follow single responsibility principle.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitRateServiceImpl implements UnitRateService {

    private final UnitDaoService unitDaoService;
    private final RateMapper rateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<DefaultRateGetResource> retrieveDefaultRate(String unitId) {
        log.debug("Retrieving default rate for unit: {}", unitId);

        UnitModel unit = findUnitById(unitId);
        DefaultRateGetResource response = rateMapper.toGetResource(unit.getDefaultRate());

        return ResponseEntity.ok(response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<DefaultRateGetResource> updateDefaultRate(String unitId, DefaultRatePatchResource patchResource) {
        log.debug("Updating default rate for unit: {}", unitId);

        UnitModel unit = findUnitById(unitId);

        if (isCreationScenario(unit)) {
            handleRateCreation(unit, patchResource);
        } else {
            handleRateUpdate(unit, patchResource);
        }

        unit = unitDaoService.save(unit);
        DefaultRateGetResource response = rateMapper.toGetResource(unit.getDefaultRate());

        return ResponseEntity.ok(response);
    }

    // ========================================
    // PRIVATE HELPER METHODS - Clean Implementation
    // ========================================

    /**
     * Finds unit by ID using the DAO layer.
     * Centralized unit retrieval logic.
     */
    private UnitModel findUnitById(String unitId) {
        return unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
    }

    /**
     * Checks if this is a creation scenario (no existing default rate).
     */
    private boolean isCreationScenario(UnitModel unit) {
        return unit.getDefaultRate() == null;
    }

    /**
     * Handles rate creation scenario.
     * Creates a new RateEmbeddable from patch resource.
     */
    private void handleRateCreation(UnitModel unit, DefaultRatePatchResource patchResource) {
        log.debug("Creating new default rate for unit: {}", unit.getId());
        RateEmbeddable newRate = rateMapper.fromPatchResource(patchResource);
        unit.setDefaultRate(newRate);
    }

    /**
     * Handles rate update scenario.
     * Updates existing RateEmbeddable with patch values.
     */
    private void handleRateUpdate(UnitModel unit, DefaultRatePatchResource patchResource) {
        log.debug("Updating existing default rate for unit: {}", unit.getId());
        rateMapper.updateFromPatchResource(patchResource, unit.getDefaultRate());
    }
}