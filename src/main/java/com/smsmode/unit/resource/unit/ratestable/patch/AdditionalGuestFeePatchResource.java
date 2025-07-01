/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable.patch;

import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Resource representing additional guest fee information in PATCH requests.
 * Contains validation rules for guest fee configuration.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
public class AdditionalGuestFeePatchResource {

    @Positive(message = "Fee per person per night must be positive")
    private Integer feePPPN;

    @Positive(message = "Guest count must be positive")
    private Integer guestCount;
}