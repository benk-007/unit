/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.RatesTableModel_;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

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

    /*private RatesTableSpecification() {
    }
    public static Specification<RatesTableModel> withIdEqual(String rateId) {
        if (!StringUtils.hasText(rateId)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(RateModel_.id), rateId);
    }
    public static Specification<RatesTableModel> withRateNameContaining(String rateName) {
        if (!StringUtils.hasText(rateName)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(RateModel_.rateName)),
                        "%" + rateName.toLowerCase() + "%"
                );
    }
    *//**
     * Creates a specification to filter rate tables by unit ID.
     * Filters rates that are associated with the specified unit.
     *//*
    public static Specification<RatesTableModel> withUnitIdEqual(String unitId) {
        if (!StringUtils.hasText(unitId)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<RatesTableModel, UnitModel> unitsJoin = root.join(RateModel_.units);
            return criteriaBuilder.equal(unitsJoin.get(UnitModel_.id), unitId);
        };
    }

    *//**
     * Creates a specification to search rates by associated unit name (case-insensitive partial match).
     * Returns rates that are linked to units whose name contains the search term.
     *//*
    public static Specification<RatesTableModel> withUnitNameContaining(String unitName) {
        if (!StringUtils.hasText(unitName)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<RatesTableModel, UnitModel> unitsJoin = root.join(RateModel_.units);
            return criteriaBuilder.like(
                    criteriaBuilder.lower(unitsJoin.get(UnitModel_.name)),
                    "%" + unitName.toLowerCase() + "%"
            );
        };
    }*/
}
