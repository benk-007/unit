/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Jul 2025</p>
 */
@Data
public class BaseRatePostResource {

    @NotNull(message = "Nightly rate is required")
    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;

    @Positive(message = "Weekend night rate must be positive")
    private Integer weekendNight;

    @Positive(message = "Weekly rate must be positive")
    private Integer weekly;

    @Positive(message = "Monthly rate must be positive")
    private Integer monthly;

    @NotNull(message = "Minimum stay is required")
    @Positive(message = "Minimum stay must be positive")
    private Integer minStay;

    @Positive(message = "Maximum stay must be positive")
    private Integer maxStay;
}
