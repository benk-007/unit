/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.RatesTableModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RatesTableDaoService {

    RatesTableModel save(RatesTableModel ratesTableModel);

    Page<RatesTableModel> findAllBy(Specification<RatesTableModel> specification, Pageable pageable);

    RatesTableModel findOneBy(Specification<RatesTableModel> specification);

    void deleteBy(Specification<RatesTableModel> specification);
}