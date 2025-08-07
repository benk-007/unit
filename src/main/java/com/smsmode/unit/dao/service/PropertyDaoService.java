package com.smsmode.unit.dao.service;

import com.smsmode.unit.model.PropertyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyDaoService {
    PropertyModel save(PropertyModel property);

    Page<PropertyModel> findAll(Pageable pageable);
}
