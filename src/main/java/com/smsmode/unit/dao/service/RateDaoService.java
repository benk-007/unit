/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.RateModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RateDaoService {

    RateModel save(RateModel rateModel);

    Page<RateModel> findAllBy(Specification<RateModel> specification, Pageable pageable);

    RateModel findOneBy(Specification<RateModel> specification);

    void deleteBy(Specification<RateModel> specification);
}