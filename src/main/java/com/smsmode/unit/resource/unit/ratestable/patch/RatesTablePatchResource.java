/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable.patch;

import jakarta.validation.Valid;
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
public class RatesTablePatchResource {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Valid
    private RatePatchResource rate;
    @Valid
    private Set<DaySpecificPricingPatchResource> daySpecificPrices;
}
