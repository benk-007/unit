/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Embeddable class representing rate information for a unit.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RateEmbeddable {

    private BigDecimal nightly;
    private BigDecimal weekendNight;
    private BigDecimal weekly;
    private BigDecimal monthly;
    private int minStay = 1;
    private int maxStay = 365;
    private BigDecimal feePPPN;
    private int guestCount = 2;
}