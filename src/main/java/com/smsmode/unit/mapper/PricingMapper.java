/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.calendar.CalendarPriceGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 08 Jul 2025</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PricingMapper {

    @Mapping(target = "unit", source = "unitModel")
    @Mapping(target = "prices", ignore = true)
    public abstract CalendarPriceGetResource unitModelToCalendarPriceGetResource(UnitModel unitModel);
}
