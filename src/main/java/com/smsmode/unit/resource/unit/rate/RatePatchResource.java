/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RatePatchResource {

    private String rateName;

    private LocalDate fromDate;

    private LocalDate untilDate;

    @Valid
    private DefaultRatePatchResource rate;

    @Valid
    private List<DaySpecificPricingPostResource> daySpecificPricings = new ArrayList<>();
}