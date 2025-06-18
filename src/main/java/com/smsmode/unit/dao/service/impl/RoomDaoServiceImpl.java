/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.service.impl;

import com.smsmode.unit.dao.repository.RoomRepository;
import com.smsmode.unit.dao.service.RoomDaoService;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.model.RoomModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 09 Jun 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoomDaoServiceImpl implements RoomDaoService {

    private final RoomRepository roomRepository;

    @Override
    public RoomModel findOneBy(Specification<RoomModel> specification) {
        return roomRepository.findOne(specification).orElseThrow(
                () -> {
                    log.debug("Couldn't find any room with the specified criteria");
                    return new ResourceNotFoundException(
                            ResourceNotFoundExceptionTitleEnum.ROOM_NOT_FOUND,
                            "No room found with the specified criteria");
                });
    }

    @Override
    public Page<RoomModel> findAllBy(Specification<RoomModel> specification, Pageable pageable) {
        return roomRepository.findAll(specification, pageable);
    }

    @Override
    public RoomModel save(RoomModel roomModel) {
        return roomRepository.save(roomModel);
    }

    @Override
    public void deleteBy(Specification<RoomModel> specification) {
        roomRepository.delete(specification);
    }

    @Override
    public void delete(RoomModel room) {
        roomRepository.delete(room);
    }
}
