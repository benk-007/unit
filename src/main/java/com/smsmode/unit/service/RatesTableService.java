/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service;

import com.smsmode.unit.resource.unit.ratestable.RatesTableGetResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTableItemGetResource;
import com.smsmode.unit.resource.unit.ratestable.patch.RatesTablePatchResource;
import com.smsmode.unit.resource.unit.ratestable.RatesTablePostResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 30 Jun 2025</p>
 */
public interface RatesTableService {
    ResponseEntity<Page<RatesTableItemGetResource>> retrieveAll(String search, String unitId, Pageable pageable);

    ResponseEntity<RatesTableGetResource> create(RatesTablePostResource ratesTablePostResource);

    ResponseEntity<RatesTableGetResource> updateById(String ratesTableId, RatesTablePatchResource ratesTablePatchResource);

    ResponseEntity<Void> deleteById(String ratesTableId);

}
