/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.model.UnitModel_;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
public class UnitSpecification {
    public static Specification<UnitModel> withIdEqual(String unitId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UnitModel_.id), unitId);
    }
}
