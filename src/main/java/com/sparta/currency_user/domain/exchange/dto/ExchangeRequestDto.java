package com.sparta.currency_user.domain.exchange.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExchangeRequestDto {
   @NotNull(message = "원화 금액은 필수 입력값입니다.")
   @DecimalMin(value = "1.0", message = "원화 금액은 1 이상이어야 합니다.")
   private BigDecimal amountInKrw;

   @NotNull(message = "환전할 통화 ID는 필수 입력값입니다.")
   @Positive(message = "통화 ID는 양수여야 합니다.")
   private Long toCurrencyId;

   @NotBlank(message = "사용자 ID는 필수 입력값입니다.")
   @Positive(message = "사용자 ID는 양수여야 합니다.")
   private Long toUserId;


}
