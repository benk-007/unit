/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.RateModel;
import com.smsmode.unit.model.RateModel_;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
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
    /**
     * Creates a specification to filter rate tables by unit ID.
     * Filters rates that are associated with the specified unit.
     */
    public static Specification<RateModel> withUnitIdEqual(String unitId) {
        if (!StringUtils.hasText(unitId)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<RateModel, UnitModel> unitsJoin = root.join(RateModel_.units);
            return criteriaBuilder.equal(unitsJoin.get(UnitModel_.id), unitId);
        };
    }

    /**
     * Creates a specification to search rates by associated unit name (case-insensitive partial match).
     * Returns rates that are linked to units whose name contains the search term.
     */
    public static Specification<RateModel> withUnitNameContaining(String unitName) {
        if (!StringUtils.hasText(unitName)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<RateModel, UnitModel> unitsJoin = root.join(RateModel_.units);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(unitsJoin.get(UnitModel_.name)),
                    "%" + unitName.toLowerCase() + "%"
            );
        };
    }
}
