package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.repository.PropertyRepository;
import com.smsmode.unit.dao.service.PropertyDaoService;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.model.PropertyModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyDaoServiceImpl implements PropertyDaoService {

    private final PropertyRepository propertyRepository;

    @Override
    public PropertyModel save(PropertyModel property) {
        return propertyRepository.save(property);
    }

    @Override
    public Page<PropertyModel> findAll(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    @Override
    public PropertyModel findOneBy(Specification<PropertyModel> specification) {
        return propertyRepository.findOne(specification).orElseThrow(() -> {
            log.debug("Couldn't find any property with the specified criteria");
            return new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.PROPERTY_NOT_FOUND,
                    "No property found with the specified criteria");
        });
    }


}
