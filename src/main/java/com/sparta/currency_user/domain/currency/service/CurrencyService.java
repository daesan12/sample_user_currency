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

    /**
     * 특정 통화 조회
     * @param id 통화 ID
     * @return 통화 응답 DTO
     */
    public CurrencyResponseDto findById(Long id) {
        Currency currency = findCurrencyById(id);
        return CurrencyResponseDto.toDto(currency);
    }

    /**
     * 통화 ID로 통화 엔티티 조회
     * @param id 통화 ID
     * @return 통화 엔티티
     */
    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("통화를 찾을 수 없습니다."));
    }

    /**
     * 모든 통화 조회
     * @return 통화 응답 DTO 리스트
     */
    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    /**
     * 통화 저장
     * @param currencyRequestDto 통화 생성 요청 DTO
     * @return 저장된 통화
     */
    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return CurrencyResponseDto.toDto(savedCurrency);
    }

    /**
     * 환율 유효성 검사
     * 스프링 애플리케이션 시작 시 호출 가능 (@PostConstruct)
     */
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
