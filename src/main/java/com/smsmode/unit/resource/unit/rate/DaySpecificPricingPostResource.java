/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class DaySpecificPricingPostResource {

    @NotEmpty(message = "At least one day must be selected")
    private Set<DayOfWeek> daysOfWeek = new LinkedHashSet<>();

    @Positive(message = "PP/PN fee must be positive when provided")
    private Integer ppPn;

    @Positive(message = "Guest count must be positive when provided")
    private Integer guestCount;

    @NotNull(message = "Nightly rate is required")
    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;


}