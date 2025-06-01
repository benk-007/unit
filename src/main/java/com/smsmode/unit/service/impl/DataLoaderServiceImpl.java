/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.embeddable.AddressEmbeddable;
import com.smsmode.unit.embeddable.ContactEmbeddable;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.service.DataLoaderService;
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

    private final UnitDaoService unitDaoService;

    @Override
    public void populateUnits() {
        UnitModel apptOasis = new UnitModel();
        apptOasis.setName("Appartement");
        apptOasis.setSubtitle("Appartement de rêve pour un séjour inoubliable");
        ContactEmbeddable oasisContact = new ContactEmbeddable();
        oasisContact.setMobile("+212623847854");
        AddressEmbeddable oasisAddress = new AddressEmbeddable();
        oasisAddress.setCity("Casablanca");
        oasisAddress.setCountry("MA");
        oasisAddress.setStreet1("5 rue maréchal george");
        oasisAddress.setStreet2("Imm Luxe Oasis, Appt N°31");
        apptOasis.setAddress(oasisAddress);
        apptOasis.setContact(oasisContact);

        UnitModel apptBelvedere = new UnitModel();
        apptBelvedere.setName("Appartement en résidence - Belevedere");
        apptBelvedere.setSubtitle("Magnifique appartement Art déco");
        ContactEmbeddable belevedereContact = new ContactEmbeddable();
        belevedereContact.setMobile("+212637889900");
        AddressEmbeddable belvedereAddress = new AddressEmbeddable();
        belvedereAddress.setCity("Casablanca");
        belvedereAddress.setCountry("MA");
        belvedereAddress.setStreet1("Rue des consulats");
        belvedereAddress.setStreet2("Résidence belle vue, Appt N°2");
        apptBelvedere.setAddress(belvedereAddress);
        apptBelvedere.setContact(belevedereContact);

        UnitModel apptMaarif = new UnitModel();
        apptMaarif.setName("Studio Maârif");
        apptMaarif.setSubtitle("Studio moderne en plein centre de Casa");
        ContactEmbeddable maarifContact = new ContactEmbeddable();
        maarifContact.setMobile("+212618348502");
        AddressEmbeddable maarifAddress = new AddressEmbeddable();
        maarifAddress.setCity("Casablanca");
        maarifAddress.setCountry("MA");
        maarifAddress.setStreet1("21 avenue karim el bahar");
        maarifAddress.setStreet2("Coin vert, Appt N°21, 2ème étage");
        apptMaarif.setAddress(maarifAddress);
        apptMaarif.setContact(maarifContact);

        unitDaoService.save(apptOasis);
        unitDaoService.save(apptBelvedere);
        unitDaoService.save(apptMaarif);
    }

    @Override
    public void run(String... args) throws Exception {
        this.populateUnits();
    }

}
