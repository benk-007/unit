/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.embeddable;

import com.smsmode.unit.enumeration.converter.DayOfWeekSetConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DaySpecificPricingEmbeddable {

    @NotEmpty(message = "At least one day must be selected")
    @Convert(converter = DayOfWeekSetConverter.class)
    @Column(name = "daysOfWeek", length = 20)
    private Set<DayOfWeek> daysOfWeek = new LinkedHashSet<>();

    @NotNull(message = "Nightly rate is required")
    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;

    @Positive(message = "PP/PN fee must be positive")
    private Integer ppPn;

    @Positive(message = "Guest count must be positive")
    private Integer guestCount;
}