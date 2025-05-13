/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.UnitController;
import com.smsmode.unit.resource.unit.UnitGetResource;
import com.smsmode.unit.resource.unit.UnitItemGetResource;
import com.smsmode.unit.resource.unit.UnitPostResource;
import com.smsmode.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 10 Apr 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class UnitControllerImpl implements UnitController {

    private final UnitService unitService;

    @Override
    public ResponseEntity<Page<UnitItemGetResource>> getAllUnits(String search, Pageable pageable) {
        return unitService.retrieveAllByPage(search, pageable);
    }

    @Override
    public ResponseEntity<UnitGetResource> getUnitById(String unitId) {
        return unitService.retrieveById(unitId);
    }

    @Override
    public ResponseEntity<UnitItemGetResource> postUnit(UnitPostResource unitPostResource) {
        return unitService.create(unitPostResource);
    }
}
