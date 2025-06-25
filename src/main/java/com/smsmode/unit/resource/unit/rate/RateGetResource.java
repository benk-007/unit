/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smsmode.unit.resource.common.AuditGetResource;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class RateGetResource {

    private String id;

    private String rateName;

    private LocalDate fromDate;

    private LocalDate untilDate;

    private DefaultRateGetResource rate;

    private List<DaySpecificPricingGetResource> daySpecificPricings = new ArrayList<>();

    private AuditGetResource audit;
}