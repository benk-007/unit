/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.UnitRateController;
import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import com.smsmode.unit.service.UnitRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of UnitRateController for managing unit rates.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@RestController
@RequiredArgsConstructor
public class UnitRateControllerImpl implements UnitRateController {

    private final UnitRateService unitRateService;

    @Override
    public ResponseEntity<DefaultRateGetResource> getDefaultRate(String unitId) {
        return unitRateService.retrieveDefaultRate(unitId);
    }
}