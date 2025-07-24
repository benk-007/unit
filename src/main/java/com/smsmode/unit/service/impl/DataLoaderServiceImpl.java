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

/*    @Override
    public void populateUnits() {
        AddressEmbeddable address = new AddressEmbeddable();
        address.setCity("Marrakech");
        address.setCountry("MA");
        address.setStreet1("Avenue du Président Kennedy, Hivernage");
        address.setPostCode("40000");

        UnitPostResource doubleGarden = new UnitPostResource();
        doubleGarden.setName("Deluxe Room, Double Bed, Garden View");
        doubleGarden.setNature(UnitNatureEnum.MULTI_UNIT);
        doubleGarden.setQuantity(4);
        doubleGarden.setSubUnitPrefix("DR-DB-GV-");
        doubleGarden.setAddress(address);

        UnitPostResource doublePool = new UnitPostResource();
        doublePool.setName("Deluxe Room, Double Bed, Pool View");
        doublePool.setNature(UnitNatureEnum.MULTI_UNIT);
        doublePool.setQuantity(7);
        doublePool.setSubUnitPrefix("DR-DB-PV-");
        doublePool.setAddress(address);

        UnitPostResource doubleDeluxeGarden = new UnitPostResource();
        doubleDeluxeGarden.setName("Premium Room, Double Bed, Balcony Garden View");
        doubleDeluxeGarden.setNature(UnitNatureEnum.MULTI_UNIT);
        doubleDeluxeGarden.setQuantity(5);
        doubleDeluxeGarden.setSubUnitPrefix("PR-DB-BGV-");
        doubleDeluxeGarden.setAddress(address);

        UnitPostResource doubleDeluxePool = new UnitPostResource();
        doubleDeluxePool.setName("Premium Room, Double Bed, Balcony Pool View");
        doubleDeluxePool.setNature(UnitNatureEnum.MULTI_UNIT);
        doubleDeluxePool.setQuantity(4);
        doubleDeluxePool.setSubUnitPrefix("PR-DB-BPV-");
        doubleDeluxePool.setAddress(address);

        UnitPostResource suitePool = new UnitPostResource();
        suitePool.setName("Junior Suite, King Size Bed, Pool View");
        suitePool.setNature(UnitNatureEnum.MULTI_UNIT);
        suitePool.setQuantity(2);
        suitePool.setSubUnitPrefix("JS-KSB-PV-");
        suitePool.setAddress(address);

        UnitPostResource suiteDeluxePool = new UnitPostResource();
        suiteDeluxePool.setName("Premium Suite, King Size Bed, Pool View");
        suiteDeluxePool.setNature(UnitNatureEnum.SINGLE);
        suiteDeluxePool.setAddress(address);

        unitService.create(doubleGarden);
        unitService.create(doublePool);
        unitService.create(doubleDeluxeGarden);
        unitService.create(doubleDeluxePool);
        unitService.create(suitePool);
        unitService.create(suiteDeluxePool);

    }*/

    @Override
    public void run(String... args) throws Exception {
//        this.populateUnits();
    }

}
