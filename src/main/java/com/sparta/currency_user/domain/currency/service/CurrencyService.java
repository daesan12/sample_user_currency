package com.sparta.currency_user.domain.currency.service;

import com.sparta.currency_user.domain.currency.dto.CurrencyRequestDto;
import com.sparta.currency_user.domain.currency.dto.CurrencyResponseDto;
import com.sparta.currency_user.domain.currency.entity.Currency;
import com.sparta.currency_user.domain.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyResponseDto findById(Long id) {
        Currency currency = findCurrencyById(id);
        return CurrencyResponseDto.toDto(currency);
    }

    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return CurrencyResponseDto.toDto(savedCurrency);
    }

    // 환율 유효성 검사
    public void validateExchangeRates() {
        List<Currency> currencies = currencyRepository.findAll();
        for (Currency currency : currencies) {
            BigDecimal exchangeRate = currency.getExchangeRate();
            if (exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
                log.warn("유효하지 않은 환율 발견 - 통화: {}, 환율: {}", currency.getCurrencyName(), exchangeRate);
            }
        }
    }
}
