/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable;

import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.rate.DaySpecificPricingGetResource;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
@Data
public class RatesTableGetResource {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private RateGetResource rate;
    private Set<DaySpecificPricingGetResource> daySpecificPrices;
    private AuditGetResource audit;
}
