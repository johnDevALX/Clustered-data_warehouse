package com.ekene.clustereddata_warehouse.model;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FxDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotNull(message = "Ordering currency Field be null")
    private Currency orderingCurrencyISOCode;

    @NotNull(message = "To currency Field cannot be null")
    private Currency toCurrencyISOCode;

    @NotNull(message = "Field cannot be null")
    private LocalDateTime dealTimeStamp;

    @NotNull(message = "Deal amount Field cannot be null")
    private BigDecimal dealAmount;
}
