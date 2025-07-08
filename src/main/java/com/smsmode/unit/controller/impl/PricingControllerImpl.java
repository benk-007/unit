/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.PricingController;
import com.smsmode.unit.resource.calendar.CalendarPriceGetResource;
import com.smsmode.unit.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */
@RestController
@RequiredArgsConstructor
public class PricingControllerImpl implements PricingController {

    private final PricingService pricingService;


    @Override
    public Page<CalendarPriceGetResource> getCalendarPricing(LocalDate checkinDate, LocalDate checkoutDate, Pageable pageable) {
        return pricingService.retrieveCalendarPrices(checkinDate, checkoutDate, pageable);
    }
}
