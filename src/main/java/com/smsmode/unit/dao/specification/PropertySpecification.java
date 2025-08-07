package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.PropertyModel;
import com.smsmode.unit.model.PropertyModel_;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecification {

    public static Specification<PropertyModel> withIdEqual(String id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PropertyModel_.id), id);
    }

}
