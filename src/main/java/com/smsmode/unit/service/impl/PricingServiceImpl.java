/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RatesTableDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.RatesTableSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.embeddable.DaySpecificPricingEmbeddable;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.mapper.PricingMapper;
import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.calendar.CalendarPriceGetResource;
import com.smsmode.unit.resource.pricing.PricingGetResource;
import com.smsmode.unit.service.PricingService;
import com.smsmode.unit.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jul 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final UnitDaoService unitDaoService;
    private final PricingMapper pricingMapper;
    private final RatesTableDaoService ratesTableDaoService;

    @Override
    public Page<CalendarPriceGetResource> retrieveCalendarPrices(LocalDate startDate, LocalDate endDate,
                                                                 Pageable pageable) {
        log.debug("Retrieve list of dates from: {} to {} ...", DateUtils.format(startDate), DateUtils.format(endDate));
        List<LocalDate> dates = DateUtils.getDatesBetweenInclusive(startDate, endDate);
        log.info("List of dates is: {}", dates.stream().map(DateUtils::format).collect(Collectors.joining(",")));
        log.debug("Constructing unit specification with readiness true and nature is SINGLE ...");
        Specification<UnitModel> unitSpecification = Specification.where(UnitSpecification.withReadiness(true)
                .and(UnitSpecification.withNature(UnitNatureEnum.SINGLE)));
        log.debug("Retrieving units from database ...");
        Page<UnitModel> unitPages = unitDaoService.findAllBy(unitSpecification, pageable);
        log.info("Retrieved {} units from database", unitPages.getTotalElements());
        log.debug("Will map units to Calendar Price resources ...");
        Page<CalendarPriceGetResource> calendarPriceGetResources = unitPages.map(pricingMapper::unitModelToCalendarPriceGetResource);
        log.debug("Grouping units by parentUnitId ...");
        Map<String, List<UnitModel>> groupedByParent = unitPages.getContent().stream()
                .collect(Collectors.groupingBy(unit -> unit.getParent() != null ? unit.getParent().getId() : ""));
        log.debug("Looping through the grouped elements ...");
        Map<String, Map<LocalDate, PricingGetResource>> pricesByUnit = new HashMap<>();
        groupedByParent.forEach((parentUnitId, subUnits) -> {
            if (!parentUnitId.isBlank()) {
                log.debug("ParentUnitId is not empty, will retrieve pricing for multi unit ...");
                Map<LocalDate, PricingGetResource> pricesPerDay = this.calculateCalendarPricesByUnit(subUnits.getFirst().getParent(), startDate, startDate, dates);
                log.info("Prices per day are: {}", pricesPerDay);
                for (UnitModel unit : subUnits) {
                    pricesByUnit.put(unit.getId(), pricesPerDay);
                }
            } else {
                log.debug("ParentUnitId is null, will retrieve pricing for each unit ...");
                for (UnitModel unit : subUnits) {
                    pricesByUnit.put(unit.getId(), this.calculateCalendarPricesByUnit(unit, startDate, endDate, dates));
                }
            }
        });
        for (CalendarPriceGetResource resource : calendarPriceGetResources.getContent()) {
            String unitId = resource.getUnit().getId();
            Map<LocalDate, PricingGetResource> prices = pricesByUnit.getOrDefault(unitId, new HashMap<>());
            resource.setPrices(prices);
        }

        return calendarPriceGetResources;
    }

    private Map<LocalDate, PricingGetResource> calculateCalendarPricesByUnit(UnitModel unitModel, LocalDate startDate, LocalDate endDate, List<LocalDate> dates) {
        Map<LocalDate, PricingGetResource> unitPrices = new HashMap<>();
        log.debug("Constructing rates table specification by unitId: {}, minStay greater than or equal to 1, startDate " +
                        "of table is before: {}, endDate of table is after: {}", unitModel.getId(),
                DateUtils.format(endDate), DateUtils.format(startDate));
        Specification<RatesTableModel> specification = Specification.where(
                RatesTableSpecification.withUnitId(unitModel.getId()).and(
                                RatesTableSpecification.withMinStayGreaterThanOrEqual(1))
                        .and(RatesTableSpecification.withStartsDateBeforeOrEqual(endDate))
                        .and(RatesTableSpecification.withEndDateAfterOrEqual(startDate)));
        log.debug("Retrieving rates table from database ...");
        Page<RatesTableModel> ratesTableModels = ratesTableDaoService.findAllBy(specification, Pageable.unpaged());
        log.info("Retrieved {} rates table from database", ratesTableModels.getTotalElements());
        for (LocalDate calendarDate : dates) {
            boolean priceSpecified = false;
            for (RatesTableModel ratesTable : ratesTableModels.getContent()) {
                List<LocalDate> ratesTableDates = DateUtils.getDatesBetweenInclusive(ratesTable.getStartDate(), ratesTable.getEndDate());
                if (priceSpecified) {
                    break;
                }
                if (ratesTableDates.stream().anyMatch(date -> date.isEqual(calendarDate))) {
                    priceSpecified = true;
                    if (CollectionUtils.isEmpty(ratesTable.getDaySpecificPrices())) {
                        log.debug("No specific day prices found, will set nightly price of rates table");
                        if (DateUtils.isWeekend(calendarDate)) {
                            unitPrices.put(calendarDate, new PricingGetResource(ratesTable.getRate().getBasePricing().getWeekendNight()));
                        } else {
                            unitPrices.put(calendarDate, new PricingGetResource(ratesTable.getRate().getBasePricing().getNightly()));
                        }
                    } else {
                        boolean daySpecificPriceSpecified = false;
                        for (DaySpecificPricingEmbeddable daySpecificPricing : ratesTable.getDaySpecificPrices()) {
                            if (daySpecificPricing.getDaysOfWeek().stream().anyMatch(dayOfWeek -> dayOfWeek.equals(calendarDate.getDayOfWeek()))) {
                                daySpecificPriceSpecified = true;
                                unitPrices.put(calendarDate, new PricingGetResource(daySpecificPricing.getNightly()));
                                break;
                            }
                        }
                        if (!daySpecificPriceSpecified) {
                            if (DateUtils.isWeekend(calendarDate)) {
                                unitPrices.put(calendarDate, new PricingGetResource(ratesTable.getRate().getBasePricing().getWeekendNight()));
                            } else {
                                unitPrices.put(calendarDate, new PricingGetResource(ratesTable.getRate().getBasePricing().getNightly()));
                            }
                        }
                    }
                }
            }
            if (!priceSpecified) {
                if (DateUtils.isWeekend(calendarDate)) {
                    unitPrices.put(calendarDate, new PricingGetResource(unitModel.getDefaultRate().getBasePricing().getWeekendNight()));
                } else {
                    unitPrices.put(calendarDate, new PricingGetResource(unitModel.getDefaultRate().getBasePricing().getNightly()));
                }
            }
        }
        return unitPrices;
    }


    @Override

    public List<PricingGetResource> retrievePricingByUnit(String unitId, LocalDate checkinDate, LocalDate checkoutDate, int guests) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int nights = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        log.info("Number of nights to stay from checkinDate: {} and checkoutDate: {} is: {}", checkinDate.format(formatter), checkoutDate.format(formatter), nights);
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        RateEmbeddable defaultRate;
        Set<RatesTableModel> ratesTables;
        if (!ObjectUtils.isEmpty(unit.getParent())) {
            defaultRate = unit.getParent().getDefaultRate();
            ratesTables = unit.getParent().getRateTables();
        } else {
            defaultRate = unit.getDefaultRate();
            ratesTables = unit.getRateTables();
        }

        List<LocalDate> bookingDates = checkinDate.datesUntil(checkoutDate).toList();
        log.info("Dates that will be booked are: {}", bookingDates.stream().map(date -> date.format(formatter)).collect(Collectors.joining(", ")));

        for (LocalDate bookingDate : bookingDates) {
            PricingGetResource pricingGetResource = new PricingGetResource();
            if (CollectionUtils.isEmpty(ratesTables)) {
                //check by order other ratesTable


            } else {
//                pricingGetResource.setDate(bookingDate);
                if (DateUtils.isWeekend(bookingDate)) {
                    pricingGetResource.setPrice(defaultRate.getBasePricing().getWeekendNight());
                } else {
                    pricingGetResource.setPrice(defaultRate.getBasePricing().getNightly());
                }
            }

        }

        return List.of();
    }
}
