/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import lombok.Data;

import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class DaySpecificPricingGetResource {

    private Set<DayOfWeek> daysOfWeek = new LinkedHashSet<>();

    private Integer nightly;

    private Integer ppPn;

    private Integer guestCount;
}