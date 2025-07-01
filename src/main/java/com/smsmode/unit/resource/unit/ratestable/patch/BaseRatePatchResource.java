/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable.patch;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 02 Jul 2025</p>
 */
@Data
public class BaseRatePatchResource {
    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;

    @Positive(message = "Weekend night rate must be positive")
    private Integer weekendNight;

    @Positive(message = "Weekly rate must be positive")
    private Integer weekly;

    @Positive(message = "Monthly rate must be positive")
    private Integer monthly;

    @Positive(message = "Minimum stay must be positive")
    private Integer minStay;

    @Positive(message = "Maximum stay must be positive")
    private Integer maxStay;
}
