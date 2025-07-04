/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.rate;

import com.smsmode.unit.resource.unit.ratestable.patch.AdditionalGuestFeePatchResource;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Resource representing default rate information for a unit in PATCH requests.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Data
public class DefaultRatePatchResource {

    @Valid
    private RentalBaseRatePatchResource rentalBaseRate;

    @Valid
    private AdditionalGuestFeePatchResource additionalGuestFee;
}