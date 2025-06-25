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

    // UNIT RATES

    @Mapping(source = "rentalBaseRate", target = "rentalBaseRate")
    @Mapping(source = "additionalGuestFee", target = "additionalGuestFee")
    public abstract DefaultRateGetResource toGetResource(RateEmbeddable embeddable);


    @Mapping(source = "rentalBaseRate", target = "rentalBaseRate")
    @Mapping(source = "additionalGuestFee", target = "additionalGuestFee")
    public abstract RateEmbeddable fromPatchResource(DefaultRatePatchResource patchResource);


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

    // RATE TABLES

    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "daySpecificPricings", target = "daySpecificPricings")
    public abstract RateModel postResourceToModel(RatePostResource ratePostResource);


    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "daySpecificPricings", target = "daySpecificPricings")
    public abstract RateGetResource modelToGetResource(RateModel rateModel);


    public abstract DaySpecificPricingEmbeddable daySpecificPricingPostToEmbeddable(
            DaySpecificPricingPostResource daySpecificPricingPostResource);


    public abstract DaySpecificPricingGetResource daySpecificPricingEmbeddableToGetResource(
            DaySpecificPricingEmbeddable daySpecificPricingEmbeddable);


    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);


    // POST-MAPPING METHODS

    @AfterMapping
    public void afterPostResourceToModel(RatePostResource ratePostResource, @MappingTarget RateModel rateModel) {
        log.debug("Mapped rate table '{}' for period {} to {}",
                rateModel.getRateName(),
                rateModel.getFromDate(),
                rateModel.getUntilDate());
    }


    @AfterMapping
    public void afterModelToGetResource(RateModel rateModel, @MappingTarget RateGetResource rateGetResource) {
        rateGetResource.setAudit(this.modelToAuditResource(rateModel));
    }


    // PROTECTED IMPLEMENTATION - MapStruct Generated

    protected abstract RentalBaseRateGetResource rentalBaseRateToGetResource(RentalBaseRateEmbeddable embeddable);

    protected abstract AdditionalGuestFeeGetResource additionalGuestFeeToGetResource(AdditionalGuestFeeEmbeddable embeddable);

    protected abstract RentalBaseRateEmbeddable rentalBaseRatePatchToEmbeddable(RentalBaseRatePatchResource patchResource);

    protected abstract AdditionalGuestFeeEmbeddable additionalGuestFeePatchToEmbeddable(AdditionalGuestFeePatchResource patchResource);

    protected abstract void updateRentalBaseRate(RentalBaseRatePatchResource patchResource, @MappingTarget RentalBaseRateEmbeddable existingEmbeddable);

    protected abstract void updateAdditionalGuestFee(AdditionalGuestFeePatchResource patchResource, @MappingTarget AdditionalGuestFeeEmbeddable existingEmbeddable);
}