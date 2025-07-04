/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.mapper.RatesTableMapper;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import com.smsmode.unit.resource.unit.rate.DefaultRatePatchResource;
import com.smsmode.unit.service.UnitRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of UnitRateService for managing unit default rates.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitRateServiceImpl implements UnitRateService {

    private final UnitDaoService unitDaoService;
    private final RatesTableMapper ratesTableMapper;

    @Override
    public ResponseEntity<DefaultRateGetResource> retrieveDefaultRate(String unitId) {
/*        log.debug("Retrieving default rate for unit: {}", unitId);

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        DefaultRateGetResource response = ratesTableMapper.embeddableToGetResource(unit.getDefaultRate());

        log.debug("Successfully retrieved default rate for unit: {}", unitId);
        return ResponseEntity.ok(response);*/
        return null;
    }

    @Override
    public ResponseEntity<DefaultRateGetResource> updateDefaultRate(String unitId, DefaultRatePatchResource patchResource) {
        /*log.debug("Updating default rate for unit: {}", unitId);

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));

        // Handle creation or update scenario
        if (unit.getDefaultRate() == null) {
            // Creation scenario: create new RateEmbeddable from patch resource
            log.debug("Creating new default rate for unit: {}", unitId);
            RateEmbeddable newRate = ratesTableMapper.patchResourceToEmbeddable(patchResource);
            unit.setDefaultRate(newRate);
        } else {
            // Update scenario: update existing RateEmbeddable
            log.debug("Updating existing default rate for unit: {}", unitId);
            ratesTableMapper.updateEmbeddableFromPatchResource(patchResource, unit.getDefaultRate());
        }

        // Save the updated unit
        unit = unitDaoService.save(unit);

        // Transform to response resource
        DefaultRateGetResource response = ratesTableMapper.embeddableToGetResource(unit.getDefaultRate());

        log.info("Successfully updated default rate for unit: {}", unitId);
        return ResponseEntity.ok(response);*/
        return null;
    }
}