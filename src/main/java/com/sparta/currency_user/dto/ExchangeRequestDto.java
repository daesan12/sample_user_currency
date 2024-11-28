package com.sparta.currency_user.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExchangeRequestDto {
   private BigDecimal amountInKrw;
   private Long toCurrencyId;
   private Long toUserId;


}
