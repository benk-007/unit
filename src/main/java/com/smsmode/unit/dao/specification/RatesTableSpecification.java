/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.embeddable.BasePricingEmbeddable_;
import com.smsmode.unit.embeddable.RateEmbeddable_;
import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.RatesTableModel_;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

public class RatesTableSpecification {


    public static Specification<RatesTableModel> withUnitId(String unitId) {
        return (root, query, criteriaBuilder) -> {
            Join<RatesTableModel, UnitModel> join = root.join(RatesTableModel_.unit);
            return criteriaBuilder.equal(join.get(UnitModel_.id), unitId);
        };
    }

    public static Specification<RatesTableModel> withNameLike(String name) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(name)) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(RatesTableModel_.name)),
                        "%" + name.toLowerCase() + "%"
                );
            }
        };
    }

    public static Specification<RatesTableModel> withIdEqual(String ratesTableId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(RatesTableModel_.id), ratesTableId);
    }

    public static Specification<RatesTableModel> withMinStayGreaterThanOrEqual(int numberOfNights) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get(RatesTableModel_.RATE).get(RateEmbeddable_.BASE_PRICING).get(BasePricingEmbeddable_.MIN_STAY),
                        numberOfNights);
    }

    public static Specification<RatesTableModel> withStartsDateBeforeOrEqual(LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get(RatesTableModel_.startDate), endDate);
    }

    public static Specification<RatesTableModel> withEndDateAfterOrEqual(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get(RatesTableModel_.endDate), startDate);
    }


}
