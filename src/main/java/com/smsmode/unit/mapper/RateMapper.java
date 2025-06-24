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
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RateMapper {

    /**
     * Maps RateEmbeddable to DefaultRateGetResource.
     *
     * @param rateEmbeddable the rate embeddable
     * @return the default rate get resource
     */
    public abstract DefaultRateGetResource embeddableToDefaultRateGetResource(RateEmbeddable rateEmbeddable);

    /**
     * Maps DefaultRatePatchResource to RateEmbeddable for updates.
     *Custom implementation to handle null values properly.
     * @param patchResource the patch resource
     * @param existingRate the existing rate embeddable to update
     * @return the updated rate embeddable
     */
    public RateEmbeddable patchResourceToEmbeddable(DefaultRatePatchResource patchResource, RateEmbeddable existingRate) {
        if (patchResource == null) {
            return existingRate;
        }

        // Mise à jour explicite de chaque champ (y compris les null)
        existingRate.setNightly(patchResource.getNightly());
        existingRate.setWeekendNight(patchResource.getWeekendNight());
        existingRate.setWeekly(patchResource.getWeekly());
        existingRate.setMonthly(patchResource.getMonthly());
        existingRate.setMinStay(patchResource.getMinStay());
        existingRate.setMaxStay(patchResource.getMaxStay());
        existingRate.setGuestCount(patchResource.getGuestCount());
        existingRate.setFeePPPN(patchResource.getFeePPPN());

        return existingRate;
    }
    /**
     * Creates a new RateEmbeddable from DefaultRatePatchResource.
     *
     * @param patchResource the patch resource
     * @return the new rate embeddable
     */
    public abstract RateEmbeddable patchResourceToNewEmbeddable(DefaultRatePatchResource patchResource);

}
