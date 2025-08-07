package com.smsmode.unit.dao.repository;

import com.smsmode.unit.model.PropertyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyModel, String>, JpaSpecificationExecutor<PropertyModel> {
}
