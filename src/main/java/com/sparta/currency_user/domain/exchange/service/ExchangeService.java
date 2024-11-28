package com.sparta.currency_user.domain.exchange.service;

import com.sparta.currency_user.domain.currency.service.CurrencyService;
import com.sparta.currency_user.domain.exchange.dto.ExchangeRequestDto;
import com.sparta.currency_user.domain.exchange.dto.ExchangeResponseDto;
import com.sparta.currency_user.domain.currency.entity.Currency;
import com.sparta.currency_user.domain.exchange.entity.Exchange;
import com.sparta.currency_user.domain.user.entity.User;
import com.sparta.currency_user.domain.exchange.repository.ExchangeRepository;
import com.sparta.currency_user.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final UserService userService;
    private final CurrencyService currencyService;
    private final ExchangeRepository exchangeRepository;

    @Transactional
    public void save(ExchangeRequestDto requestDto) {
        // User 및 Currency 객체 조회
        User user = userService.findUserById(requestDto.getToUserId());
        Currency currency = currencyService.findCurrencyById(requestDto.getToCurrencyId());

        // 환율 계산
        BigDecimal exchangeRate = currency.getExchangeRate();
        BigDecimal amountAfterExchange  = requestDto.getAmountInKrw().divide(exchangeRate, 2, RoundingMode.HALF_UP);

        // Exchange 객체 생성 및 저장
        Exchange exchange = new Exchange(user, currency, requestDto.getAmountInKrw(), amountAfterExchange);
        Exchange change = exchangeRepository.save(exchange);

    }

    public List<ExchangeResponseDto> findByUserId(Long userId) {
        // 특정 사용자의 환전 내역 조회
        List<Exchange> exchanges = exchangeRepository.findByUserId(userId);

        // 조회된 Entity 를 DTO로 변환
        return exchanges.stream().
                map(ExchangeResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExchangeResponseDto  update(Long id) {
        Exchange exchange = exchangeRepository.findById(id).
                orElseThrow(()->new IllegalArgumentException("해당 환전요청번호가 존재하지않습니다."+id));

        // 상태를 변경
        exchange.toggleStatus();

        return  ExchangeResponseDto.toDto(exchange);
    }
}
