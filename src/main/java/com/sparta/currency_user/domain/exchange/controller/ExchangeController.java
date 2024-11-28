package com.sparta.currency_user.domain.exchange.controller;

import com.sparta.currency_user.domain.exchange.dto.ExchangeRequestDto;
import com.sparta.currency_user.domain.exchange.dto.ExchangeResponseDto;
import com.sparta.currency_user.domain.exchange.dto.FindGroupResponseDto;
import com.sparta.currency_user.domain.exchange.service.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;
    @PostMapping
    public ResponseEntity<String> createExchange(@Valid @RequestBody ExchangeRequestDto requestDto) {
        exchangeService.save(requestDto);
        return ResponseEntity.ok().body("등록완료");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ExchangeResponseDto>> findByid(@PathVariable Long id) {
        List<ExchangeResponseDto> response = exchangeService.findByUserId(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExchangeResponseDto> updateExchange(@PathVariable Long id) {
        ExchangeResponseDto  updatedExchange = exchangeService.update(id);
        return ResponseEntity.ok(updatedExchange);
    }

    @GetMapping("/group")
    public ResponseEntity<List<FindGroupResponseDto>> findByGroup() {

        List<FindGroupResponseDto> groups = exchangeService.findGroup();
        return ResponseEntity.ok(groups);
    }

}