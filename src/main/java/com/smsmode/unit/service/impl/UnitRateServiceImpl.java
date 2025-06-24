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

    @Override
    public ResponseEntity<DefaultRateGetResource> retrieveDefaultRate(String unitId) {
        log.debug("Retrieving default rate for unit: {}", unitId);

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        DefaultRateGetResource defaultRateGetResource = rateMapper.embeddableToDefaultRateGetResource(unit.getDefaultRate());

        return ResponseEntity.ok(defaultRateGetResource);
    }

    @Override
    public ResponseEntity<DefaultRateGetResource> updateDefaultRate(String unitId, DefaultRatePatchResource patchResource) {
        log.debug("Updating default rate for unit: {}", unitId);

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));

        RateEmbeddable updatedRate;
        if (unit.getDefaultRate() == null) {
            // Create new rate if none exists
            updatedRate = rateMapper.patchResourceToNewEmbeddable(patchResource);
        } else {
            // Update existing rate
            updatedRate = rateMapper.patchResourceToEmbeddable(patchResource, unit.getDefaultRate());
        }

        unit.setDefaultRate(updatedRate);
        unit = unitDaoService.save(unit);

        DefaultRateGetResource response = rateMapper.embeddableToDefaultRateGetResource(unit.getDefaultRate());
        return ResponseEntity.ok(response);
    }
}