/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.RoomSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.mapper.UnitMapper;
import com.smsmode.unit.model.RoomModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.unit.details.RoomPatchResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsGetResource;
import com.smsmode.unit.resource.unit.details.UnitDetailsPatchResource;
import com.smsmode.unit.service.UnitDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 06 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitDetailsServiceImpl implements UnitDetailsService {

    private final UnitDaoService unitDaoService;
    private final RoomDaoService roomDaoService;
    private final UnitMapper unitMapper;

    @Override
    public ResponseEntity<UnitDetailsGetResource> retrieveDetailsById(String unitId) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        return ResponseEntity.ok(unitMapper.modelToDetailsGetResource(unit));
    }

    @Override
    public ResponseEntity<UnitDetailsGetResource> update(String unitId, UnitDetailsPatchResource unitDetailsPatchResource) {
        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));
        unit = unitMapper.detailsPatchResourceToModel(unitDetailsPatchResource, unit);
        //Rooms handling logic
        if (!CollectionUtils.isEmpty(unitDetailsPatchResource.getRooms())) {
            unit.removeAllRooms();
            for (RoomPatchResource room : unitDetailsPatchResource.getRooms()) {
                //new room
                if (ObjectUtils.isEmpty(room.getId())) {
                    RoomModel roomModel = unitMapper.roomPatchResourceToModel(room);
                    unit.addRoom(roomModel);
                } else {
                    //existing room
                    RoomModel roomModel = roomDaoService.findOneBy(RoomSpecification.withIdEqual(room.getId()));
                    roomModel = unitMapper.roomPatchResourceToModel(room, roomModel);
                    unit.removeRoom(roomModel);
                    unit.addRoom(roomModel);
                }
            }
        }

        unit = unitDaoService.save(unit);
        return ResponseEntity.ok(unitMapper.modelToDetailsGetResource(unit));
    }
}
