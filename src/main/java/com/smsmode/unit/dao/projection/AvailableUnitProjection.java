/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.dao.projection;

import com.smsmode.unit.enumeration.UnitNatureEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 28 Jul 2025</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableUnitProjection {
    private String id;
    private String name;
    private UnitNatureEnum nature;
}
