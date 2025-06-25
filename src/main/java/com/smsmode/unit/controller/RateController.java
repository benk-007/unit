/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.unit.rate.RateGetResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/settings/rates/tables")
public interface RateController {

    @GetMapping
    ResponseEntity<Page<RateGetResource>> getRates(@RequestParam(value = "search", required = false) String search, Pageable pageable);

    @PostMapping
    ResponseEntity<RateGetResource> postRate(@RequestBody @Valid RatePostResource ratePostResource);
}