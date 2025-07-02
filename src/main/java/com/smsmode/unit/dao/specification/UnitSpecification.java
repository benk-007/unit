package com.smsmode.unit.dao.specification;

import com.smsmode.unit.enumeration.UnitNatureEnum;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class UnitSpecification {

    public static Specification<UnitModel> withIdEqual(String unitId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(UnitModel_.id), unitId);
    }

    public static Specification<UnitModel> withNameLike(String unitName) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(unitName)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(UnitModel_.name)),
                    "%" + unitName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<UnitModel> withSubtitleLike(String subtitle) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(subtitle)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(UnitModel_.subtitle)),
                    "%" + subtitle.toLowerCase() + "%"
            );
        };
    }


    public static Specification<UnitModel> withNature(UnitNatureEnum nature) {
        return (root, query, criteriaBuilder) -> {
            if (nature == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get(UnitModel_.nature), nature);
        };
    }

    public static Specification<UnitModel> withParentFilter(Boolean withParent) {
        return (root, query, criteriaBuilder) -> {
            if (withParent == null) {
                return criteriaBuilder.conjunction();
            }
            if (withParent) {
                return criteriaBuilder.isNotNull(root.get(UnitModel_.parentUnit));
            } else {
                return criteriaBuilder.isNull(root.get(UnitModel_.parentUnit));
            }
        };
    }

    public static Specification<UnitModel> withParentUnitId(String parentId) {
        return (root, query, cb) -> {
            if (parentId == null) {
                return null;
            }
            return cb.equal(root.get("parentUnit").get("id"), parentId);
        };
    }
}
