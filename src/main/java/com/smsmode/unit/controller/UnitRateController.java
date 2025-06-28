/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import com.smsmode.unit.resource.unit.rate.DefaultRatePatchResource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller interface for managing unit rates.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@RequestMapping("/units/{unitId}/rates")
public interface UnitRateController {

    /**
     * Retrieves the default rate for a specific unit.
     */
    @GetMapping("/default")
    ResponseEntity<DefaultRateGetResource> getDefaultRate(@PathVariable("unitId") String unitId);

    /**
     * Updates the default rate for a specific unit.
     */
    @PatchMapping("/default")
    ResponseEntity<DefaultRateGetResource> patchDefaultRate(@PathVariable("unitId") String unitId, @Valid @RequestBody DefaultRatePatchResource patchResource);

}