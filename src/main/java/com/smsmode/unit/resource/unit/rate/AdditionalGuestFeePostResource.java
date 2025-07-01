/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Jul 2025</p>
 */
@Data
public class AdditionalGuestFeePostResource {
    @Positive(message = "Fee per person per night must be positive")
    private Integer feePpPn;

    @Positive(message = "Guest count must be positive")
    private Integer guestCount;
}
