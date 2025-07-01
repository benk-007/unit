/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Resource representing default rate information for a unit in GET responses.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DefaultRateGetResource {
    private RentalBaseRateGetResource rentalBaseRate;
    private AdditionalGuestFeeGetResource additionalGuestFee;
}