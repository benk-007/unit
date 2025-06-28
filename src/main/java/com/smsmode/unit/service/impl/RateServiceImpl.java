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

/**
 * Implementation of RateService for managing rate tables.
 * Follows the same pattern as UnitServiceImpl, RoomServiceImpl, etc.
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created [current date]</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateDaoService rateDaoService;
    private final RateMapper rateMapper;

    @Override
    public ResponseEntity<RateGetResource> create(RatePostResource ratePostResource) {
        log.debug("Creating rate table: '{}'", ratePostResource.getRateName());

        RateModel rateModel = rateMapper.postResourceToModel(ratePostResource);
        rateModel = rateDaoService.save(rateModel);
        RateGetResource response = rateMapper.modelToGetResource(rateModel);

        log.debug("Rate table '{}' created successfully with ID: {}", rateModel.getRateName(), rateModel.getId());
        return ResponseEntity.created(URI.create("")).body(response);
    }

    @Override
    public ResponseEntity<Page<RateGetResource>> retrieveAll(String search, Pageable pageable) {
        log.debug("Retrieving rate tables with search: '{}', page: {}, size: {}",
                search, pageable.getPageNumber(), pageable.getPageSize());

        Specification<RateModel> specification = Specification
                .where(RateSpecification.withRateNameContaining(search));

        Page<RateModel> rateModelsPage = rateDaoService.findAllBy(specification, pageable);
        Page<RateGetResource> response = rateModelsPage.map(rateMapper::modelToGetResource);

        log.debug("Retrieved {} rate tables for search: '{}'", response.getNumberOfElements(), search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RateGetResource> update(String rateId, RatePatchResource ratePatchResource) {
        log.debug("Updating rate table with ID: '{}'", rateId);

        RateModel rateModel = rateDaoService.findOneBy(RateSpecification.withIdEqual(rateId));
        rateModel = rateMapper.patchResourceToModel(ratePatchResource, rateModel);
        rateModel = rateDaoService.save(rateModel);
        RateGetResource response = rateMapper.modelToGetResource(rateModel);

        log.debug("Rate table '{}' updated successfully", rateModel.getRateName());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> delete(String rateId) {
        log.debug("Deleting rate table with ID: '{}'", rateId);

        // Validate existence (throws exception if not found)
        RateModel rateModel = rateDaoService.findOneBy(RateSpecification.withIdEqual(rateId));

        rateDaoService.deleteBy(RateSpecification.withIdEqual(rateId));

        log.debug("Rate table '{}' deleted successfully", rateModel.getRateName());
        return ResponseEntity.noContent().build();
    }
}