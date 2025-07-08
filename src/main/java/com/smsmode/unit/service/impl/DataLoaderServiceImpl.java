/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RatesTableDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.embeddable.*;
import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    private final UnitDaoService unitDaoService;
    private final RatesTableDaoService ratesTableDaoService;

    @Override
    public void populateUnits() {
        UnitModel apptOasis = new UnitModel();
        apptOasis.setName("Appartement");
        apptOasis.setSubTitle("Appartement de rêve pour un séjour inoubliable");
        ContactEmbeddable oasisContact = new ContactEmbeddable();
        oasisContact.setMobile("+212623847854");
        AddressEmbeddable oasisAddress = new AddressEmbeddable();
        oasisAddress.setCity("Casablanca");
        oasisAddress.setCountry("MA");
        oasisAddress.setStreet1("5 rue maréchal george");
        oasisAddress.setStreet2("Imm Luxe Oasis, Appt N°31");
        apptOasis.setAddress(oasisAddress);
        apptOasis.setContact(oasisContact);
        apptOasis.setReadiness(true);

        UnitModel apptBelvedere = new UnitModel();
        apptBelvedere.setName("Appartement en résidence - Belevedere");
        apptBelvedere.setSubTitle("Magnifique appartement Art déco");
        ContactEmbeddable belevedereContact = new ContactEmbeddable();
        belevedereContact.setMobile("+212637889900");
        AddressEmbeddable belvedereAddress = new AddressEmbeddable();
        belvedereAddress.setCity("Casablanca");
        belvedereAddress.setCountry("MA");
        belvedereAddress.setStreet1("Rue des consulats");
        belvedereAddress.setStreet2("Résidence belle vue, Appt N°2");
        apptBelvedere.setAddress(belvedereAddress);
        apptBelvedere.setContact(belevedereContact);
        apptBelvedere.setReadiness(true);

        UnitModel apptMaarif = new UnitModel();
        apptMaarif.setName("Studio Maârif");
        apptMaarif.setSubTitle("Studio moderne en plein centre de Casa");
        ContactEmbeddable maarifContact = new ContactEmbeddable();
        maarifContact.setMobile("+212618348502");
        AddressEmbeddable maarifAddress = new AddressEmbeddable();
        maarifAddress.setCity("Casablanca");
        maarifAddress.setCountry("MA");
        maarifAddress.setStreet1("21 avenue karim el bahar");
        maarifAddress.setStreet2("Coin vert, Appt N°21, 2ème étage");
        apptMaarif.setAddress(maarifAddress);
        apptMaarif.setContact(maarifContact);
        apptMaarif.setReadiness(true);

        apptOasis = unitDaoService.save(apptOasis);
        apptBelvedere = unitDaoService.save(apptBelvedere);
        apptMaarif = unitDaoService.save(apptMaarif);
        populateDefaultRate(apptOasis);
        populateDefaultRate(apptBelvedere);
        populateDefaultRate(apptMaarif);

        populateRatesTable(apptOasis);
    }

    @Override
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
    }

    @Override
    public void run(String... args) throws Exception {
        this.populateUnits();
    }

}
