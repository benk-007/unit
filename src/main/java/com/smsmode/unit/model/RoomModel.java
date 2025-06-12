/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.BedEmbeddable;
import com.smsmode.unit.enumeration.RoomTypeEnum;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 07 Jun 2025</p>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_ROOM")
public class RoomModel extends AbstractBaseModel {

    @Enumerated(EnumType.STRING)
    private RoomTypeEnum type;
    private Integer bathroom;
    private Integer floorSize;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "X_BEDDING", joinColumns = @JoinColumn(name = "room_id"))
    private List<BedEmbeddable> beds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitModel unit;
}
