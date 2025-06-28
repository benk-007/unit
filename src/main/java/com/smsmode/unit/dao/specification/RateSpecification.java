/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.RateModel;
import com.smsmode.unit.model.RateModel_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class RateSpecification {

    private RateSpecification() {
    }
    public static Specification<RateModel> withIdEqual(String rateId) {
        if (!StringUtils.hasText(rateId)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(RateModel_.id), rateId);
    }
    public static Specification<RateModel> withRateNameContaining(String rateName) {
        if (!StringUtils.hasText(rateName)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(RateModel_.rateName)),
                        "%" + rateName.toLowerCase() + "%"
                );
    }
}
