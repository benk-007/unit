/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.calendar.CalendarPriceGetResource;
import com.smsmode.unit.resource.pricing.PricingGetResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */
public interface PricingService {

    Page<CalendarPriceGetResource> retrieveCalendarPrices(LocalDate checkinDate, LocalDate checkoutDate, Pageable pageable);

    List<PricingGetResource> retrievePricingByUnit(String unitId, LocalDate checkinDate, LocalDate checkoutDate, int guests);


}
