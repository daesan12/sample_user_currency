package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.UserResponseDto;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.repository.ExchangeRepository;
import com.sparta.currency_user.service.ExchangeService;
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


}
