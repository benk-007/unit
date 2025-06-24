/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.resource.unit.rate.DefaultRateGetResource;
import com.smsmode.unit.resource.unit.rate.DefaultRatePatchResource;
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
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public abstract class RateMapper {

    /**
     * Maps RateEmbeddable to DefaultRateGetResource.
     */
    public abstract DefaultRateGetResource embeddableToDefaultRateGetResource(RateEmbeddable rateEmbeddable);

    /**
     * Updates existing RateEmbeddable with values from patch resource.
     * SET_TO_NULL strategy = met à jour TOUS les champs, même ceux qui sont null.
     */
    public abstract void patchResourceToEmbeddable(DefaultRatePatchResource patchResource, @MappingTarget RateEmbeddable existingRate);

    /**
     * Creates a new RateEmbeddable from DefaultRatePatchResource.
     */
    public abstract RateEmbeddable patchResourceToNewEmbeddable(DefaultRatePatchResource patchResource);

}
