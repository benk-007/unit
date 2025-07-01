/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatePostResource {

    @NotNull
    private BaseRatePostResource basePricing;

    @Valid
    private AdditionalGuestFeePostResource additionalGuestFee;
}