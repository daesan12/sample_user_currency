package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {

    @NotBlank(message = "통화 이름은 필수 입력값입니다.")
    @Size(max = 3, message = "통화 이름은 3자 이하여야 합니다.")
    private String currencyName;

    @NotNull(message = "환율은 필수 입력값입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "환율은 0보다 커야 합니다.")
    private BigDecimal exchangeRate;

    @NotBlank(message = "통화 기호는 필수 입력값입니다.")
    @Size(max = 1, message = "통화 기호는 1자 이하여야 합니다.")
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyName,
                this.exchangeRate,
                this.symbol
        );
    }
}
