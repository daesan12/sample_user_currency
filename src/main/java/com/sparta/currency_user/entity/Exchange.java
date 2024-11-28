package com.sparta.currency_user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "Exchange")
public class Exchange extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_currency_id", nullable = false)
    private Currency currency;

    //환전 전 금액(원화 기준)
    @Column(nullable = false)
    private BigDecimal amountInKrw;

    //환전 후 금액
    @Column(nullable = false)
    private BigDecimal amountAfterExchange;


    @Column(nullable = false)
    private String status;

}
