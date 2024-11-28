package com.sparta.currency_user.domain.user.entity;

import com.sparta.currency_user.domain.exchange.entity.Exchange;
import com.sparta.currency_user.domain.entity.Base;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Exchange> exchanges;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {}


}