/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.resource.unit.UnitPostResource;
import com.smsmode.unit.service.DataLoaderService;
import com.smsmode.unit.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 31 May 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService, CommandLineRunner {

    private final UnitService unitService;

    @Override
    public void populateUnits() {
        AddressEmbeddable address = new AddressEmbeddable();
        address.setCity("Marrakech");
        address.setCountry("MA");
        address.setStreet1("Angle Avenue Mohammed VI, et Avenue Hassan II");
        address.setPostCode("40000");

        UnitPostResource doubleVuePiscine = new UnitPostResource();
        doubleVuePiscine.setName("Chambre Double Vue Piscine");
        doubleVuePiscine.setNature(UnitNatureEnum.MULTI_UNIT);
        doubleVuePiscine.setQuantity(40);
        doubleVuePiscine.setSubUnitPrefix("DVP");
        doubleVuePiscine.setAddress(address);

        UnitPostResource doubleVueGare = new UnitPostResource();
        doubleVueGare.setName("Chambre Double Vue Gare");
        doubleVueGare.setNature(UnitNatureEnum.MULTI_UNIT);
        doubleVueGare.setQuantity(30);
        doubleVueGare.setSubUnitPrefix("DVG");
        doubleVueGare.setAddress(address);

        UnitPostResource suite = new UnitPostResource();
        suite.setName("Suite");
        suite.setNature(UnitNatureEnum.MULTI_UNIT);
        suite.setQuantity(7);
        suite.setSubUnitPrefix("ST");
        suite.setAddress(address);

        unitService.create(doubleVuePiscine);
        unitService.create(doubleVueGare);
        unitService.create(suite);

    }

/*    @Override
    public void populateDefaultRate(UnitModel unitModel) {
        RateEmbeddable rate = new RateEmbeddable();
        BasePricingEmbeddable basePricing = new BasePricingEmbeddable();
        basePricing.setNightly(BigDecimal.valueOf(250));
        basePricing.setWeekendNight(BigDecimal.valueOf(300));
        basePricing.setWeekly(BigDecimal.valueOf(1700));
        basePricing.setMonthly(BigDecimal.valueOf(7000));
        basePricing.setMinStay(1);
        basePricing.setMaxStay(31);
        rate.setBasePricing(basePricing);
        unitModel.setDefaultRate(rate);
        unitDaoService.save(unitModel);
    }

    @Override
    public void populateRatesTable(UnitModel unitModel) {
        RatesTableModel ratesTableModel = new RatesTableModel();
        ratesTableModel.setName("Aid rates");
        ratesTableModel.setStartDate(LocalDate.now());
        ratesTableModel.setEndDate(LocalDate.now().plusDays(10));
        ratesTableModel.setUnit(unitModel);
        RateEmbeddable rate = new RateEmbeddable();
        BasePricingEmbeddable basePricing = new BasePricingEmbeddable();
        basePricing.setNightly(BigDecimal.valueOf(400));
        basePricing.setWeekendNight(BigDecimal.valueOf(450));
        basePricing.setMinStay(1);
        rate.setBasePricing(basePricing);
        ratesTableModel.setRate(rate);

        Set<DaySpecificPricingEmbeddable> daySpecificPricings = new HashSet<DaySpecificPricingEmbeddable>();
        daySpecificPricings.add(new DaySpecificPricingEmbeddable(Set.of(DayOfWeek.THURSDAY), BigDecimal.valueOf(500), null, null));
        daySpecificPricings.add(new DaySpecificPricingEmbeddable(Set.of(DayOfWeek.THURSDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY), BigDecimal.valueOf(550), null, null));

        ratesTableModel.setDaySpecificPrices(daySpecificPricings);

        ratesTableDaoService.save(ratesTableModel);
    }*/

    @Override
    public void run(String... args) throws Exception {
        this.populateUnits();
    }

}
