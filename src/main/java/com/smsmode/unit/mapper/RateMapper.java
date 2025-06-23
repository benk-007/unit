/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * Mapper for converting between RateEmbeddable and rate resources.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RateMapper {

    /**
     * Maps RateEmbeddable to DefaultRateGetResource.
     *
     * @param rateEmbeddable the rate embeddable
     * @return the default rate get resource
     */
    public abstract DefaultRateGetResource embeddableToDefaultRateGetResource(RateEmbeddable rateEmbeddable);
}