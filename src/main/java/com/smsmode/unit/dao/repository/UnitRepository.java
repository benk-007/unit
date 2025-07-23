/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.repository;

import com.smsmode.unit.enumeration.UnitTypeEnum;
import com.smsmode.unit.model.UnitModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 11 Apr 2025</p>
 */
@Repository
public interface UnitRepository extends JpaRepository<UnitModel, String>, JpaSpecificationExecutor<UnitModel> {
    @Modifying
    @Query("UPDATE UnitModel u SET u.type = :type WHERE u.parent.id = :parentUnitId")
    void updateTypeByParentUnitId(@Param("parentUnitId") String parentUnitId, @Param("type") UnitTypeEnum type);

    @Query("SELECT u.id FROM UnitModel u")
    List<String> findAllUnitIds();

}
