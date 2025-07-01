/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable;

import com.smsmode.unit.resource.unit.rate.DaySpecificPricingPostResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RatesTablePostResource {

    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Valid
    private RatePostResource rate;
    @Valid
    private Set<DaySpecificPricingPostResource> daySpecificPrices;
    @NotBlank
    private String unitId;
}
