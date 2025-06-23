/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing unit rates.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
public interface UnitRateService {

    /**
     * Retrieves the default rate for a specific unit.
     *
     * @param unitId the unit identifier
     * @return ResponseEntity containing the default rate information
     */
    ResponseEntity<DefaultRateGetResource> retrieveDefaultRate(String unitId);
}