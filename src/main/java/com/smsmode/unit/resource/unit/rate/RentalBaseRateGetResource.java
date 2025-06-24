/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Resource representing rental base rate information in GET responses.
 * Contains nightly, weekly, monthly rates and stay duration constraints.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class RentalBaseRateGetResource {

    private Integer nightly;
    private Integer weekendNight;
    private Integer weekly;
    private Integer monthly;
    private Integer minStay;
    private Integer maxStay;
}