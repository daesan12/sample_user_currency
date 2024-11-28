package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CurrencyResponseDto {

    private final Long id;
    private final String currencyName;
    private final BigDecimal exchangeRate;
    private final String symbol;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;



    public static CurrencyResponseDto toDto(Currency currency) {
        return new CurrencyResponseDto(
         currency.getId(),
         currency.getCurrencyName(),
         currency.getExchangeRate(),
         currency.getSymbol(),
         currency.getCreatedAt(),
         currency.getUpdatedAt()
        );
    }
}
