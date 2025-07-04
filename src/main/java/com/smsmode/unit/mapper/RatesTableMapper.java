/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.mapper;

import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.base.AbstractBaseModel;
import com.smsmode.unit.resource.common.AuditGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableItemGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTablePostResource;
import com.smsmode.unit.resource.unit.ratestable.patch.RatesTablePatchResource;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

@Slf4j
@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public abstract class RatesTableMapper {


    @Mapping(target = "basePricing", source = "rate.basePricing")
    public abstract RatesTableItemGetResource modelToRatesTableItemGetResource(RatesTableModel ratesTableModel);

    @AfterMapping
    public void afterModelToItemGetResource(RatesTableModel ratesTableModel, @MappingTarget RatesTableItemGetResource ratesTableItemGetResource) {
        ratesTableItemGetResource.setAudit(this.modelToAuditResource(ratesTableModel));
    }

    @Mapping(target = "unit", ignore = true)
    public abstract RatesTableModel postResourceToModel(RatesTablePostResource ratesTablePostResource);

    @Mapping(target = "unit", ignore = true)
    public abstract RatesTableModel patchResourceToModel(RatesTablePatchResource ratesTablePatchResource,
                                                         @MappingTarget RatesTableModel ratesTableModel);

    public abstract RatesTableGetResource modelToGetResource(RatesTableModel ratesTableModel);

    @AfterMapping
    public void afterModelToGetResource(RatesTableModel ratesTableModel, @MappingTarget RatesTableGetResource ratesTableGetResource) {
        ratesTableGetResource.setAudit(this.modelToAuditResource(ratesTableModel));
    }


    public abstract AuditGetResource modelToAuditResource(AbstractBaseModel baseModel);










/*



    // ========================================
    // DEFAULT RATE (RateEmbeddable) MAPPINGS
    // ========================================

    */
/**
 * Maps RateEmbeddable to DefaultRateGetResource.
 *//*

    public abstract DefaultRateGetResource embeddableToGetResource(RateEmbeddable embeddable);

    */
/**
 * Maps DefaultRatePatchResource to RateEmbeddable for creation scenario.
 *//*

    public abstract RateEmbeddable patchResourceToEmbeddable(DefaultRatePatchResource patchResource);

    */
/**
 * Updates existing RateEmbeddable with values from DefaultRatePatchResource.
 *//*

    @Mapping(target = "rentalBaseRate", ignore = true)
    @Mapping(target = "additionalGuestFee", ignore = true)
    public abstract void updateEmbeddableFromPatchResource(
            DefaultRatePatchResource patchResource,
            @MappingTarget RateEmbeddable existingEmbeddable);

    */
/**
 * Post-mapping method to handle nested embeddables update.
 *//*

    @AfterMapping
    public void afterUpdateEmbeddableFromPatchResource(
            DefaultRatePatchResource patchResource,
            @MappingTarget RateEmbeddable existingEmbeddable) {

        // Handle rental base rate update/creation
        if (patchResource.getRentalBaseRate() != null) {
            if (existingEmbeddable.getRentalBaseRate() == null) {
                existingEmbeddable.setRentalBaseRate(new BasePricingEmbeddable());
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
    public abstract RatesTableModel postResourceToModel(RatePostResource ratePostResource);

    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "daySpecificPricings", target = "daySpecificPricings")
    public abstract RateGetResource modelToGetResource(RatesTableModel ratesTableModel);

    */
/**
 * Updates existing RateModel with values from RatePatchResource.
 *//*

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "daySpecificPricings", ignore = true)
    public abstract RatesTableModel patchResourceToModel(RatePatchResource ratePatchResource, @MappingTarget RatesTableModel ratesTableModel);

    */
/**
 * Post-mapping method to handle nested embeddables update.
 *//*

    @AfterMapping
    public void afterPatchResourceToModel(RatePatchResource ratePatchResource, @MappingTarget RatesTableModel ratesTableModel) {
        // Handle rate configuration update/creation
        if (ratePatchResource.getRate() != null) {
            if (ratesTableModel.getRate() == null) {
                ratesTableModel.setRate(patchResourceToEmbeddable(ratePatchResource.getRate()));
            } else {
                updateEmbeddableFromPatchResource(ratePatchResource.getRate(), ratesTableModel.getRate());
            }
        }

        // Handle day-specific pricing collection replacement
        if (ratePatchResource.getDaySpecificPricings() != null) {
            ratesTableModel.getDaySpecificPricings().clear();
            ratePatchResource.getDaySpecificPricings().forEach(daySpecificPricingPost ->
                    ratesTableModel.getDaySpecificPricings().add(
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
    public void afterModelToGetResource(RatesTableModel ratesTableModel, @MappingTarget RateGetResource rateGetResource) {
        rateGetResource.setAudit(this.modelToAuditResource(ratesTableModel));
    }

    // ========================================
    // PROTECTED MAPPING METHODS FOR NESTED EMBEDDABLES
    // ========================================

    */
/**
 * Maps RentalBaseRateEmbeddable to GET resource.
 * Protected method following MapStruct convention for nested mappings.
 *//*

    protected abstract RentalBaseRateGetResource rentalBaseRateEmbeddableToGetResource(BasePricingEmbeddable embeddable);

    */
/**
 * Maps AdditionalGuestFeeEmbeddable to GET resource.
 * Protected method following MapStruct convention for nested mappings.
 *//*

    protected abstract AdditionalGuestFeeGetResource additionalGuestFeeEmbeddableToGetResource(AdditionalGuestFeeEmbeddable embeddable);

    */
/**
 * Maps RentalBaseRatePatchResource to embeddable.
 * Protected method following MapStruct convention for nested mappings.
 *//*

    protected abstract BasePricingEmbeddable rentalBaseRatePatchToEmbeddable(RentalBaseRatePatchResource patchResource);

    */
/**
 * Maps AdditionalGuestFeePatchResource to embeddable.
 * Protected method following MapStruct convention for nested mappings.
 *//*

    protected abstract AdditionalGuestFeeEmbeddable additionalGuestFeePatchToEmbeddable(AdditionalGuestFeePatchResource patchResource);

    */
/**
 * Updates existing RentalBaseRateEmbeddable with patch values.
 *//*

    protected abstract void updateRentalBaseRateEmbeddable(
            RentalBaseRatePatchResource patchResource,
            @MappingTarget BasePricingEmbeddable existingEmbeddable);

    */
/**
 * Updates existing AdditionalGuestFeeEmbeddable with patch values.
 *//*

    protected abstract void updateAdditionalGuestFeeEmbeddable(
            AdditionalGuestFeePatchResource patchResource,
            @MappingTarget AdditionalGuestFeeEmbeddable existingEmbeddable);
*/

}