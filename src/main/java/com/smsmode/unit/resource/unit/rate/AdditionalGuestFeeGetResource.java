/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Resource representing additional guest fee information in GET responses.
 * Contains fee per person per night and base guest count.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class AdditionalGuestFeeGetResource {

    private Integer feePPPN;
    private Integer guestCount;
}