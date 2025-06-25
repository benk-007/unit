/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.rate.RatePostResource;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import org.springframework.http.ResponseEntity;

public interface RateService {

    ResponseEntity<RateGetResource> create(RatePostResource ratePostResource);
}