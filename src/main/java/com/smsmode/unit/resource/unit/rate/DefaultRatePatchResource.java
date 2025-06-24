/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Resource representing default rate information for a unit in PATCH requests.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
public class DefaultRatePatchResource {

    private BigDecimal nightly;
    private BigDecimal weekendNight;
    private BigDecimal weekly;
    private BigDecimal monthly;
    private Integer minStay;
    private Integer maxStay;
    private BigDecimal feePPPN;
    private Integer guestCount;
}