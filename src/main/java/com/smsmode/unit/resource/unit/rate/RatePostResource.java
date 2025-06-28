/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.smsmode.unit.resource.unit.rate.DefaultRatePatchResource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class RatePostResource {

    @NotBlank(message = "Rate name is required")
    private String rateName;

    @NotNull(message = "From date is required")
    private LocalDate fromDate;

    @NotNull(message = "Until date is required")
    private LocalDate untilDate;

    @NotNull(message = "Rate configuration is required")
    @Valid
    private DefaultRatePatchResource rate;

    @Valid
    private List<DaySpecificPricingPostResource> daySpecificPricings = new ArrayList<>();
}