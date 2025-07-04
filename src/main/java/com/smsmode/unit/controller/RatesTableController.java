/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.ratestable.RatesTableGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableItemGetResource;
import com.smsmode.unit.resource.unit.ratestable.patch.RatesTablePatchResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTablePostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
@RequestMapping("rates-tables")
public interface RatesTableController {

    @GetMapping
    ResponseEntity<Page<RatesTableItemGetResource>> getRatesTableByPage(@RequestParam(value = "search", required = false) String search,
                                                                        @RequestParam(value = "unitId") String unitId,
                                                                        Pageable pageable);

    @GetMapping("{ratesTableId}")
    ResponseEntity<RatesTableGetResource> getRatesTableById(@PathVariable("ratesTableId") String ratesTableId);

    @PostMapping
    ResponseEntity<RatesTableGetResource> postRatesTable(@RequestBody @Valid RatesTablePostResource ratesTablePostResource);

    @PatchMapping("{ratesTableId}")
    ResponseEntity<RatesTableGetResource> patchRatesTableById(@PathVariable("ratesTableId") String ratesTableId, @RequestBody @Valid RatesTablePatchResource ratesTablePatchResource);

    @DeleteMapping("{ratesTableId}")
    ResponseEntity<Void> deleteRatesTableById(@PathVariable("ratesTableId") String ratesTableId);

}
