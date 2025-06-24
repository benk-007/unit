/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.embeddable.AdditionalGuestFeeEmbeddable;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.embeddable.RentalBaseRateEmbeddable;
import com.smsmode.unit.resource.unit.rate.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

/**
 * Mapper for converting between RateEmbeddable and rate resources.
 * Simplified design following clean code principles with clear public API.
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

    // ========================================
    // PUBLIC API - Clean and Simple
    // ========================================

    /**
     * Maps RateEmbeddable to DefaultRateGetResource for GET responses.
     * Handles null cases gracefully.
     */
    public DefaultRateGetResource toGetResource(RateEmbeddable embeddable) {
        if (embeddable == null) {
            return new DefaultRateGetResource();
        }
        return mapToGetResource(embeddable);
    }

    /**
     * Creates a new RateEmbeddable from DefaultRatePatchResource for creation scenarios.
     */
    @Mapping(source = "rentalBaseRate", target = "rentalBaseRate")
    @Mapping(source = "additionalGuestFee", target = "additionalGuestFee")
    public abstract RateEmbeddable fromPatchResource(DefaultRatePatchResource patchResource);

    /**
     * Updates existing RateEmbeddable with values from PATCH request.
     * Handles partial updates and nested structure creation.
     */
    public void updateFromPatchResource(DefaultRatePatchResource patchResource, @MappingTarget RateEmbeddable existingRate) {
        if (patchResource == null) {
            return;
        }

        // Handle rental base rate update/creation
        if (patchResource.getRentalBaseRate() != null) {
            if (existingRate.getRentalBaseRate() == null) {
                existingRate.setRentalBaseRate(new RentalBaseRateEmbeddable());
            }
            updateRentalBaseRate(patchResource.getRentalBaseRate(), existingRate.getRentalBaseRate());
        }

        // Handle additional guest fee update/creation
        if (patchResource.getAdditionalGuestFee() != null) {
            if (existingRate.getAdditionalGuestFee() == null) {
                existingRate.setAdditionalGuestFee(new AdditionalGuestFeeEmbeddable());
            }
            updateAdditionalGuestFee(patchResource.getAdditionalGuestFee(), existingRate.getAdditionalGuestFee());
        }
    }

    // ========================================
    // PROTECTED IMPLEMENTATION - MapStruct Generated
    // ========================================

    /**
     * Internal mapping method handled by MapStruct for nested structures.
     */
    @Mapping(source = "rentalBaseRate", target = "rentalBaseRate")
    @Mapping(source = "additionalGuestFee", target = "additionalGuestFee")
    protected abstract DefaultRateGetResource mapToGetResource(RateEmbeddable embeddable);

    /**
     * Maps RentalBaseRateEmbeddable to RentalBaseRateGetResource.
     * Used automatically by MapStruct in nested mapping.
     */
    protected abstract RentalBaseRateGetResource rentalBaseRateToGetResource(RentalBaseRateEmbeddable embeddable);

    /**
     * Maps AdditionalGuestFeeEmbeddable to AdditionalGuestFeeGetResource.
     * Used automatically by MapStruct in nested mapping.
     */
    protected abstract AdditionalGuestFeeGetResource additionalGuestFeeToGetResource(AdditionalGuestFeeEmbeddable embeddable);

    /**
     * Maps RentalBaseRatePatchResource to RentalBaseRateEmbeddable for creation.
     * Used automatically by MapStruct in fromPatchResource method.
     */
    protected abstract RentalBaseRateEmbeddable rentalBaseRatePatchToEmbeddable(RentalBaseRatePatchResource patchResource);

    /**
     * Maps AdditionalGuestFeePatchResource to AdditionalGuestFeeEmbeddable for creation.
     * Used automatically by MapStruct in fromPatchResource method.
     */
    protected abstract AdditionalGuestFeeEmbeddable additionalGuestFeePatchToEmbeddable(AdditionalGuestFeePatchResource patchResource);

    /**
     * Updates existing RentalBaseRateEmbeddable with values from PATCH request.
     * Used internally by updateFromPatchResource method.
     */
    protected abstract void updateRentalBaseRate(
            RentalBaseRatePatchResource patchResource,
            @MappingTarget RentalBaseRateEmbeddable existingEmbeddable);

    /**
     * Updates existing AdditionalGuestFeeEmbeddable with values from PATCH request.
     * Used internally by updateFromPatchResource method.
     */
    protected abstract void updateAdditionalGuestFee(
            AdditionalGuestFeePatchResource patchResource,
            @MappingTarget AdditionalGuestFeeEmbeddable existingEmbeddable);
}