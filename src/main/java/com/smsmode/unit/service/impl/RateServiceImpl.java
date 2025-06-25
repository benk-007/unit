/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RateDaoService;
import com.smsmode.unit.dao.specification.RateSpecification;
import com.smsmode.unit.mapper.RateMapper;
import com.smsmode.unit.model.RateModel;
import com.smsmode.unit.resource.unit.rate.RateGetResource;
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
}