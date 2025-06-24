package com.smsmode.unit.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RentalBaseRateEmbeddable{
    @NotNull(message = "Nightly rate is required")
    @Positive(message = "Nightly rate must be positive")
    private Integer nightly;

    @Positive(message = "Weekend night rate must be positive")
    private Integer weekendNight;

    @Positive(message = "Weekly rate must be positive")
    private Integer weekly;

    @Positive(message = "Monthly rate must be positive")
    private Integer monthly;

    @NotNull(message = "Minimum stay is required")
    @Positive(message = "Minimum stay must be positive")
    private Integer minStay;

    @Positive(message = "Maximum stay must be positive")
    private Integer maxStay;
}

