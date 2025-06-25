/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.repository.RateRepository;
import com.smsmode.unit.dao.service.RateDaoService;
import com.smsmode.unit.model.RateModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateDaoServiceImpl implements RateDaoService {

    private final RateRepository rateRepository;

    @Override
    public RateModel save(RateModel rateModel) {
        log.debug("Saving rate table: '{}'", rateModel.getRateName());

        RateModel savedRate = rateRepository.save(rateModel);

        log.info("Rate table '{}' saved successfully with ID: {}",
                savedRate.getRateName(), savedRate.getId());

        return savedRate;
    }
}