/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.smsmode.unit.resource.unit.rate.get.BasePricingGetResource;
import lombok.Data;

@Data
public class RateGetResource {

    private BasePricingGetResource basePricing;
    private AdditionalGuestFeeGetResource additionalGuestFee;
}