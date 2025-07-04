/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.repository.RateRepository;
import com.smsmode.unit.dao.service.RatesTableDaoService;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.model.RatesTableModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesTableDaoServiceImpl implements RatesTableDaoService {

    private final RateRepository rateRepository;

    @Override
    public RatesTableModel save(RatesTableModel ratesTableModel) {
        return rateRepository.save(ratesTableModel);
    }

    @Override
    public Page<RatesTableModel> findAllBy(Specification<RatesTableModel> specification, Pageable pageable) {
        return rateRepository.findAll(specification, pageable);
    }

    @Override
    public RatesTableModel findOneBy(Specification<RatesTableModel> specification) {
        return rateRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any rate table with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.RATE_NOT_FOUND,
                            "No rate table found with the specified criteria");
                });
    }


    @Override
    public void deleteBy(Specification<RatesTableModel> specification) {
        rateRepository.delete(specification);
    }
}