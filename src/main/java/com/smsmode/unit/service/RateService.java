/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.ratestable.patch.RatePatchResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RateService {

    ResponseEntity<RateGetResource> create(RatePostResource ratePostResource, String unitId);

    ResponseEntity<Page<RateGetResource>> retrieveAll(String search, String unitId, Pageable pageable);

    ResponseEntity<RateGetResource> update(String rateId, RatePatchResource ratePatchResource);

    ResponseEntity<Void> delete(String rateId);
}