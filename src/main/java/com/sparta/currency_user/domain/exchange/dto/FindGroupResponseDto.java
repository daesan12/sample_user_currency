package com.sparta.currency_user.domain.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
public class FindGroupResponseDto {
    private Long userId;
    private Long count;
    private BigDecimal totalAmountInKrw;
}
