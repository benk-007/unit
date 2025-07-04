/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.RatesTableController;
import com.smsmode.unit.resource.unit.ratestable.RatesTableGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableItemGetResource;
import com.smsmode.unit.resource.unit.ratestable.patch.RatesTablePatchResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTablePostResource;
import com.smsmode.unit.service.RatesTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class RatesTableControllerImpl implements RatesTableController {

    private final RatesTableService ratesTableService;

    @Override
    public ResponseEntity<Page<RatesTableItemGetResource>> getRatesTableByPage(String search, String unitId, Pageable pageable) {
        return ratesTableService.retrieveAll(search, unitId, pageable);
    }

    @Override
    public ResponseEntity<RatesTableGetResource> getRatesTableById(String ratesTableId) {
        return ratesTableService.retrieveById(ratesTableId);
    }

    @Override
    public ResponseEntity<RatesTableGetResource> postRatesTable(RatesTablePostResource ratesTablePostResource) {
        return ratesTableService.create(ratesTablePostResource);
    }

    @Override
    public ResponseEntity<RatesTableGetResource> patchRatesTableById(String ratesTableId, RatesTablePatchResource ratesTablePatchResource) {
        return ratesTableService.updateById(ratesTableId, ratesTablePatchResource);
    }

    @Override
    public ResponseEntity<Void> deleteRatesTableById(String ratesTableId) {
        return ratesTableService.deleteById(ratesTableId);
    }
}
