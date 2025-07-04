/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RatesTableDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.RatesTableSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.mapper.RatesTableMapper;
import com.smsmode.unit.model.RatesTableModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.ratestable.RatesTableGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableItemGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTablePostResource;
import com.smsmode.unit.resource.unit.ratestable.patch.RatesTablePatchResource;
import com.smsmode.unit.service.RatesTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatesTableServiceImpl implements RatesTableService {

    private final RatesTableDaoService ratesTableDaoService;
    private final UnitDaoService unitDaoService;
    private final RatesTableMapper ratesTableMapper;

    @Override
    public ResponseEntity<Page<RatesTableItemGetResource>> retrieveAll(String search, String unitId, Pageable pageable) {
        Specification<RatesTableModel> specification = Specification.where(RatesTableSpecification.withUnitId(unitId)).and(RatesTableSpecification.withNameLike(search));
        Page<RatesTableModel> ratesTableModelPage = ratesTableDaoService.findAllBy(specification, pageable);
        return ResponseEntity.ok(ratesTableModelPage.map(ratesTableMapper::modelToRatesTableItemGetResource));
    }

    @Override
    public ResponseEntity<RatesTableGetResource> retrieveById(String ratesTableId) {
        Specification<RatesTableModel> specification = Specification.where(RatesTableSpecification.withIdEqual(ratesTableId));
        RatesTableModel ratesTableModel = ratesTableDaoService.findOneBy(specification);
        return ResponseEntity.ok(ratesTableMapper.modelToGetResource(ratesTableModel));
    }

    @Override
    public ResponseEntity<RatesTableGetResource> create(RatesTablePostResource ratesTablePostResource) {
        UnitModel unitModel = unitDaoService.findOneBy(UnitSpecification.withIdEqual(ratesTablePostResource.getUnitId()));
        RatesTableModel ratesTableModel = ratesTableMapper.postResourceToModel(ratesTablePostResource);
        ratesTableModel.setUnit(unitModel);
        ratesTableModel = ratesTableDaoService.save(ratesTableModel);
        return ResponseEntity.created(URI.create("")).body(ratesTableMapper.modelToGetResource(ratesTableModel));
    }

    @Override
    public ResponseEntity<RatesTableGetResource> updateById(String ratesTableId, RatesTablePatchResource ratesTablePatchResource) {
        RatesTableModel ratesTableModel = ratesTableDaoService.findOneBy(RatesTableSpecification.withIdEqual(ratesTableId));
        ratesTableModel = ratesTableMapper.patchResourceToModel(ratesTablePatchResource, ratesTableModel);
        ratesTableModel = ratesTableDaoService.save(ratesTableModel);
        return ResponseEntity.ok(ratesTableMapper.modelToGetResource(ratesTableModel));
    }

    @Override
    public ResponseEntity<Void> deleteById(String ratesTableId) {
        ratesTableDaoService.deleteBy(RatesTableSpecification.withIdEqual(ratesTableId));
        return ResponseEntity.noContent().build();
    }
}
