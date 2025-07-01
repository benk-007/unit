/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable.patch;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class RatePatchResource {
    @Valid
    private BaseRatePatchResource basePricing;
    @Valid
    private AdditionalGuestFeePatchResource additionalGuestFee;
}