/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.model;

import com.smsmode.unit.embeddable.DaySpecificPricingEmbeddable;
import com.smsmode.unit.embeddable.RateEmbeddable;
import com.smsmode.unit.model.base.AbstractBaseModel;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "X_RATES_TABLE")
public class RatesTableModel extends AbstractBaseModel {

    @NotBlank(message = "Rate name is required")
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull(message = "From date is required")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Until date is required")
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Valid
    @Embedded
    private RateEmbeddable rate;

    @Valid
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "X_DAY_SPECIFIC_PRICING", joinColumns = @JoinColumn(name = "RATE_ID"))
    private Set<DaySpecificPricingEmbeddable> daySpecificPrices = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private UnitModel unit;
}