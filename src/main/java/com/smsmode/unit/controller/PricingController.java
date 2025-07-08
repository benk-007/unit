/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.controller;

import com.smsmode.unit.resource.calendar.CalendarPriceGetResource;
import com.smsmode.unit.resource.pricing.PricingGetResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */
@RequestMapping("/prices")
public interface PricingController {

    @GetMapping("/calendar")
    Page<CalendarPriceGetResource> getCalendarPricing(@RequestParam("checkinDate") LocalDate checkinDate,
                                                      @RequestParam("checkoutDate") LocalDate checkoutDate,
                                                      Pageable pageable);
}
