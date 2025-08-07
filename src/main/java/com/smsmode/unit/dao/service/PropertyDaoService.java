package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.PropertyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PropertyDaoService {
    PropertyModel save(PropertyModel property);

    Page<PropertyModel> findAll(Pageable pageable);

    PropertyModel findOneBy(Specification<PropertyModel> specification);

}
