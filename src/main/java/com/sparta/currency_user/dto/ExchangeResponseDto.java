package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Exchange;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExchangeResponseDto {
    private final Long id;
    private final Long toCurrencyId;
    private final Long toUserId;
    private final BigDecimal amountInKrw;
    private final String formattedAmountAfterExchange;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;




    public static ExchangeResponseDto toDto(Exchange exchange) {
        return new ExchangeResponseDto(
                exchange.getId(),
                exchange.getCurrency().getId(),
                exchange.getUser().getId(),
                exchange.getAmountInKrw(),
                formatAmountWithCurrency(exchange.getAmountAfterExchange(), exchange.getCurrency().getSymbol()), // Formatted value
                exchange.getStatus(),
                exchange.getCreatedAt(),
                exchange.getUpdatedAt()
        );
    }

    private static String formatAmountWithCurrency(BigDecimal amount, String symbol) {
        return amount.setScale(2, RoundingMode.HALF_UP) + symbol;
    }

}
