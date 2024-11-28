package com.sparta.currency_user.domain.exchange.entity;

import com.sparta.currency_user.domain.currency.entity.Currency;
import com.sparta.currency_user.domain.entity.Base;
import com.sparta.currency_user.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Column
    private String status = "normal";

    public Exchange(User user, Currency currency, BigDecimal amountInKrw, BigDecimal amountAfterExchange) {
        this.user = user;
        this.currency = currency;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
    }

    public Exchange() {

    }

    public void toggleStatus() {
        if ("cancelled".equals(this.status)) {
            this.status = "normal";
        } else if ("normal".equals(this.status)) {
            this.status = "cancelled";
        }
    }
}
