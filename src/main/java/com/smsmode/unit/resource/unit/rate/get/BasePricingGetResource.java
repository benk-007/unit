/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate.get;

import lombok.Data;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 01 Jul 2025</p>
 */
@Data
public class BasePricingGetResource {
    private Integer nightly;
    private Integer weekendNight;
    private Integer weekly;
    private Integer monthly;
    private Integer minStay;
    private Integer maxStay;
}
