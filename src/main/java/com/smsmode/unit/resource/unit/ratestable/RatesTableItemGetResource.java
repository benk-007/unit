/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.resource.unit.ratestable;

import com.smsmode.unit.embeddable.BasePricingEmbeddable;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.rate.get.BasePricingGetResource;
import lombok.Data;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
@Data
public class RatesTableItemGetResource {
    private String id;
    private String name;
    private LocalDate from;
    private LocalDate to;
    private BasePricingGetResource basePricing;
    private AuditGetResource audit;
}
