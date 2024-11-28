package com.sparta.currency_user.domain.exchange.service;

import com.sparta.currency_user.domain.currency.service.CurrencyService;
import com.sparta.currency_user.domain.exchange.dto.ExchangeRequestDto;
import com.sparta.currency_user.domain.exchange.dto.ExchangeResponseDto;
import com.sparta.currency_user.domain.currency.entity.Currency;
import com.sparta.currency_user.domain.exchange.dto.FindGroupResponseDto;
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
    /**
     *환전 요청 생성
     *@param requestDto 환전 요청 데이터
     */
    @Transactional
    public void createExchangeRequest(ExchangeRequestDto requestDto) {
        //사용자 및 통화 정보 조회
        User user = userService.findUserById(requestDto.getToUserId());
        Currency currency = currencyService.findCurrencyById(requestDto.getToCurrencyId());
        //환전 계산
        BigDecimal exchangeRate = currency.getExchangeRate();
        BigDecimal amountAfterExchange = requestDto.getAmountInKrw().divide(exchangeRate, 2, RoundingMode.HALF_UP);

        Exchange exchange = new Exchange(user, currency, requestDto.getAmountInKrw(), amountAfterExchange);
        exchangeRepository.save(exchange);
    }

    /**
     * 특정 사용자의 환전 요청 내역 조회
     * @param userId 사용자 ID
     * @return 환전 요청 내역 리스트
     */
    public List<ExchangeResponseDto> getExchangesByUserId(Long userId) {
        List<Exchange> exchanges = exchangeRepository.findByUserId(userId);

        return exchanges.stream().
                map(ExchangeResponseDto::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 환전 요청 상태 변경
     * @param id 환전 요청 ID
     * @return 변경된 환전 요청 데이터
     */
    @Transactional
    public ExchangeResponseDto update(Long id) {
        Exchange exchange = exchangeRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 환전요청번호가 존재하지않습니다." + id));
        exchange.toggleStatus();

        return ExchangeResponseDto.toDto(exchange);
    }

    /**
     * 사용자별 그룹화된 환전 요청 조회
     * @return 그룹화된 환전 요청 리스트
     */
    public List<FindGroupResponseDto> findGroup() {
        return exchangeRepository.findGroup();
    }
}
