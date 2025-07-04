/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable.patch;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 02 Jul 2025</p>
 */
@Data
public class DaySpecificPricingPatchResource {
    @NotEmpty(message = "At least one day must be selected")
    private Set<DayOfWeek> daysOfWeek = new LinkedHashSet<>();

    @Positive(message = "PP/PN fee must be positive when provided")
    private Integer ppPn;

    @Positive(message = "Guest count must be positive when provided")
    private Integer guestCount;

    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;
}
