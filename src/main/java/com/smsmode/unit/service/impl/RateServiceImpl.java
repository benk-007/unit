/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RateDaoService;
import com.smsmode.unit.dao.specification.RateSpecification;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.mapper.RateMapper;
import com.smsmode.unit.model.RateModel;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
import com.smsmode.unit.resource.unit.rate.RatePatchResource;
import com.smsmode.unit.resource.unit.rate.RatePostResource;
import com.smsmode.unit.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateDaoService rateDaoService;
    private final RateMapper rateMapper;

    @Override
    public ResponseEntity<RateGetResource> create(RatePostResource ratePostResource) {
        log.debug("Creating rate table: '{}'", ratePostResource.getRateName());

        // Transform POST resource to domain model
        RateModel rateModel = rateMapper.postResourceToModel(ratePostResource);

        // Persist the rate table
        RateModel savedRateModel = rateDaoService.save(rateModel);

        // Transform persisted model to GET resource for response
        RateGetResource rateGetResource = rateMapper.modelToGetResource(savedRateModel);

        log.info("Rate table '{}' created successfully with ID: {}",
                savedRateModel.getRateName(), savedRateModel.getId());

        // Return HTTP 201 Created with the created rate table
        return ResponseEntity.created(URI.create("")).body(rateGetResource);
    }

    @Override
    public ResponseEntity<Page<RateGetResource>> retrieveAll(String search, Pageable pageable) {
        log.debug("Retrieving rate tables with search: '{}', page: {}, size: {}",
                search, pageable.getPageNumber(), pageable.getPageSize());

        // Build dynamic specification based on search criteria
        Specification<RateModel> specification = Specification
                .where(RateSpecification.withRateNameContaining(search));

        // Retrieve paginated rate tables from DAO layer
        Page<RateModel> rateModelsPage = rateDaoService.findAllBy(specification, pageable);

        // Transform models to resources while preserving pagination
        Page<RateGetResource> rateResourcesPage = rateModelsPage.map(rateMapper::modelToGetResource);

        log.debug("Retrieved {} rate tables (total: {}) for search: '{}'",
                rateResourcesPage.getNumberOfElements(),
                rateResourcesPage.getTotalElements(),
                search);

        // Return HTTP 200 OK with paginated results
        return ResponseEntity.ok(rateResourcesPage);
    }

    @Override
    public ResponseEntity<RateGetResource> update(String rateId, RatePatchResource ratePatchResource) {
        log.debug("Updating rate table with ID: '{}'", rateId);

        // Retrieve existing rate table (throws ResourceNotFoundException if not found)
        RateModel existingRateModel = rateDaoService.findOneBy(RateSpecification.withIdEqual(rateId));

        // Apply partial updates following Edit Default Rate pattern
        applyPartialUpdates(ratePatchResource, existingRateModel);

        // Persist the updated rate table
        RateModel updatedRateModel = rateDaoService.save(existingRateModel);

        // Transform updated model to GET resource for response
        RateGetResource rateGetResource = rateMapper.modelToGetResource(updatedRateModel);

        log.info("Rate table '{}' updated successfully with ID: {}",
                updatedRateModel.getRateName(), updatedRateModel.getId());

        // Return HTTP 200 OK with updated rate table
        return ResponseEntity.ok(rateGetResource);
    }


    @Override
    public ResponseEntity<Void> delete(String rateId) {
        log.debug("Deleting rate table with ID: '{}'", rateId);

        // Validate rate table existence (throws ResourceNotFoundException if not found)
        // This also serves as a business validation step
        RateModel existingRateModel = rateDaoService.findOneBy(RateSpecification.withIdEqual(rateId));

        log.debug("Rate table '{}' found, proceeding with deletion", existingRateModel.getRateName());

        // Perform hard deletion via DAO service
        rateDaoService.deleteBy(RateSpecification.withIdEqual(rateId));

        log.info("Rate table '{}' deleted successfully with ID: {}",
                existingRateModel.getRateName(), rateId);

        // Return HTTP 204 No Content for successful deletion (REST standard)
        return ResponseEntity.noContent().build();
    }


    private void applyPartialUpdates(RatePatchResource ratePatchResource, RateModel existingRateModel) {
        // Update basic fields when provided (null-safe)
        if (ratePatchResource.getRateName() != null) {
            existingRateModel.setRateName(ratePatchResource.getRateName());
        }

        if (ratePatchResource.getFromDate() != null) {
            existingRateModel.setFromDate(ratePatchResource.getFromDate());
        }

        if (ratePatchResource.getUntilDate() != null) {
            existingRateModel.setUntilDate(ratePatchResource.getUntilDate());
        }

        // Handle rate configuration update/creation (same logic as Edit Default Rate)
        if (ratePatchResource.getRate() != null) {
            if (existingRateModel.getRate() == null) {
                // Creation scenario: Create new RateEmbeddable from patch resource
                log.debug("Creating new rate configuration for rate table: {}", existingRateModel.getId());
                RateEmbeddable newRate = rateMapper.fromPatchResource(ratePatchResource.getRate());
                existingRateModel.setRate(newRate);
            } else {
                // Update scenario: Use existing updateFromPatchResource logic
                log.debug("Updating existing rate configuration for rate table: {}", existingRateModel.getId());
                rateMapper.updateFromPatchResource(ratePatchResource.getRate(), existingRateModel.getRate());
            }
        }

        // Handle day-specific pricing collection replacement when provided
        if (ratePatchResource.getDaySpecificPricings() != null) {
            log.debug("Updating day-specific pricing for rate table: {} (replacing {} rules with {})",
                    existingRateModel.getId(),
                    existingRateModel.getDaySpecificPricings().size(),
                    ratePatchResource.getDaySpecificPricings().size());

            // Clear existing rules and replace with new ones
            existingRateModel.getDaySpecificPricings().clear();
            ratePatchResource.getDaySpecificPricings().forEach(daySpecificPricingPost ->
                    existingRateModel.getDaySpecificPricings().add(
                            rateMapper.daySpecificPricingPostToEmbeddable(daySpecificPricingPost)
                    )
            );
        }
    }



}