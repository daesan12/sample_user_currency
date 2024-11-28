package com.sparta.currency_user.exception;

import com.sparta.currency_user.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyValidator {
    private final CurrencyService currencyService;

    @PostConstruct
    public void validateStartup(){
        log.info("통화,환율 유효성 검사시작");
        currencyService.validateExchangeRates();
        log.info("통화,환율 유효성 검사완료");
    }
}
