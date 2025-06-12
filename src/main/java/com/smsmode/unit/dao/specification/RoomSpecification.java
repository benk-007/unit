/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.specification;

import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.model.RoomModel_;
import org.springframework.data.jpa.domain.Specification;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Jun 2025</p>
 */
public class RoomSpecification {
    public static Specification<RoomModel> withIdEqual(String roomId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RoomModel_.id), roomId);
    }
}
