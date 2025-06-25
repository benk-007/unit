/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.RateController;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import com.smsmode.unit.resource.unit.rate.RatePatchResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import com.smsmode.unit.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RateControllerImpl implements RateController {

    private final RateService rateService;

    @Override
    public ResponseEntity<Page<RateGetResource>> getRates(String search, Pageable pageable) {
        return rateService.retrieveAll(search, pageable);
    }


    @Override
    public ResponseEntity<RateGetResource> postRate(RatePostResource ratePostResource) {
        return rateService.create(ratePostResource);
    }

    @Override
    public ResponseEntity<RateGetResource> patchRate(String rateId, RatePatchResource ratePatchResource) {
        return rateService.update(rateId, ratePatchResource);
    }
}