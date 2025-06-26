/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.embeddable.AdditionalGuestFeeEmbeddable;
import com.smsmode.unit.embeddable.DaySpecificPricingEmbeddable;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.embeddable.RentalBaseRateEmbeddable;
import com.smsmode.unit.model.RateModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.rate.DaySpecificPricingGetResource;
import com.smsmode.unit.resource.unit.rate.DaySpecificPricingPostResource;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import com.smsmode.unit.resource.unit.rate.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public abstract class RateMapper {

    // ========================================
    // DEFAULT RATE (RateEmbeddable) MAPPINGS
    // ========================================

    /**
     * Maps RateEmbeddable to DefaultRateGetResource.
     */
    public abstract DefaultRateGetResource embeddableToGetResource(RateEmbeddable embeddable);

    /**
     * Maps DefaultRatePatchResource to RateEmbeddable for creation scenario.
     */
    public abstract RateEmbeddable patchResourceToEmbeddable(DefaultRatePatchResource patchResource);

    /**
     * Updates existing RateEmbeddable with values from DefaultRatePatchResource.
     */
    @Mapping(target = "rentalBaseRate", ignore = true)
    @Mapping(target = "additionalGuestFee", ignore = true)
    public abstract void updateEmbeddableFromPatchResource(
            DefaultRatePatchResource patchResource,
            @MappingTarget RateEmbeddable existingEmbeddable);

    /**
     * Post-mapping method to handle nested embeddables update.
     */
    @AfterMapping
    public void afterUpdateEmbeddableFromPatchResource(
            DefaultRatePatchResource patchResource,
            @MappingTarget RateEmbeddable existingEmbeddable) {

        // Handle rental base rate update/creation
        if (patchResource.getRentalBaseRate() != null) {
            if (existingEmbeddable.getRentalBaseRate() == null) {
                existingEmbeddable.setRentalBaseRate(new RentalBaseRateEmbeddable());
            }
            updateRentalBaseRateEmbeddable(patchResource.getRentalBaseRate(), existingEmbeddable.getRentalBaseRate());
        }

        // Handle additional guest fee update/creation
        if (patchResource.getAdditionalGuestFee() != null) {
            if (existingEmbeddable.getAdditionalGuestFee() == null) {
                existingEmbeddable.setAdditionalGuestFee(new AdditionalGuestFeeEmbeddable());
            }
            updateAdditionalGuestFeeEmbeddable(patchResource.getAdditionalGuestFee(), existingEmbeddable.getAdditionalGuestFee());
        }
    }

    // ========================================
    // RATE TABLE (RateModel) MAPPINGS
    // ========================================

    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "daySpecificPricings", target = "daySpecificPricings")
    public abstract RateModel postResourceToModel(RatePostResource ratePostResource);

    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "daySpecificPricings", target = "daySpecificPricings")
    public abstract RateGetResource modelToGetResource(RateModel rateModel);

    /**
     * Updates existing RateModel with values from RatePatchResource.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "daySpecificPricings", ignore = true)
    public abstract RateModel patchResourceToModel(RatePatchResource ratePatchResource, @MappingTarget RateModel rateModel);

    /**
     * Post-mapping method to handle nested embeddables update.
     */
    @AfterMapping
    public void afterPatchResourceToModel(RatePatchResource ratePatchResource, @MappingTarget RateModel rateModel) {
        // Handle rate configuration update/creation
        if (ratePatchResource.getRate() != null) {
            if (rateModel.getRate() == null) {
                rateModel.setRate(patchResourceToEmbeddable(ratePatchResource.getRate()));
            } else {
                updateEmbeddableFromPatchResource(ratePatchResource.getRate(), rateModel.getRate());
            }
        }

        // Handle day-specific pricing collection replacement
        if (ratePatchResource.getDaySpecificPricings() != null) {
            rateModel.getDaySpecificPricings().clear();
            ratePatchResource.getDaySpecificPricings().forEach(daySpecificPricingPost ->
                    rateModel.getDaySpecificPricings().add(
                            daySpecificPricingPostToEmbeddable(daySpecificPricingPost)
                    )
            );
        }
    }

    public abstract DaySpecificPricingEmbeddable daySpecificPricingPostToEmbeddable(
            DaySpecificPricingPostResource daySpecificPricingPostResource);

    public abstract DaySpecificPricingGetResource daySpecificPricingEmbeddableToGetResource(
            DaySpecificPricingEmbeddable daySpecificPricingEmbeddable);

    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);

    @AfterMapping
    public void afterModelToGetResource(RateModel rateModel, @MappingTarget RateGetResource rateGetResource) {
        rateGetResource.setAudit(this.modelToAuditResource(rateModel));
    }

    // ========================================
    // PROTECTED MAPPING METHODS FOR NESTED EMBEDDABLES
    // ========================================

    /**
     * Maps RentalBaseRateEmbeddable to GET resource.
     * Protected method following MapStruct convention for nested mappings.
     */
    protected abstract RentalBaseRateGetResource rentalBaseRateEmbeddableToGetResource(RentalBaseRateEmbeddable embeddable);

    /**
     * Maps AdditionalGuestFeeEmbeddable to GET resource.
     * Protected method following MapStruct convention for nested mappings.
     */
    protected abstract AdditionalGuestFeeGetResource additionalGuestFeeEmbeddableToGetResource(AdditionalGuestFeeEmbeddable embeddable);

    /**
     * Maps RentalBaseRatePatchResource to embeddable.
     * Protected method following MapStruct convention for nested mappings.
     */
    protected abstract RentalBaseRateEmbeddable rentalBaseRatePatchToEmbeddable(RentalBaseRatePatchResource patchResource);

    /**
     * Maps AdditionalGuestFeePatchResource to embeddable.
     * Protected method following MapStruct convention for nested mappings.
     */
    protected abstract AdditionalGuestFeeEmbeddable additionalGuestFeePatchToEmbeddable(AdditionalGuestFeePatchResource patchResource);

    /**
     * Updates existing RentalBaseRateEmbeddable with patch values.
     */
    protected abstract void updateRentalBaseRateEmbeddable(
            RentalBaseRatePatchResource patchResource,
            @MappingTarget RentalBaseRateEmbeddable existingEmbeddable);

    /**
     * Updates existing AdditionalGuestFeeEmbeddable with patch values.
     */
    protected abstract void updateAdditionalGuestFeeEmbeddable(
            AdditionalGuestFeePatchResource patchResource,
            @MappingTarget AdditionalGuestFeeEmbeddable existingEmbeddable);
}