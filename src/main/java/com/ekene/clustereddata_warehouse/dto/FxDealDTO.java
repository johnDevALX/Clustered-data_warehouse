package com.ekene.clustereddata_warehouse.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FxDealDTO {
    private Long id;

    @NotBlank(message = "valid currency code is required")
    private String orderingCurrencyISOCode;

    @NotBlank(message = "valid currency code is required")
    private String toCurrencyISOCode;

    @NotNull
    private LocalDateTime dealTimeStamp = LocalDateTime.now();

    @Min(value = 1L, message = "Amount should not be empty")
    private BigDecimal dealAmount;
}
