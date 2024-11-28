package com.sparta.currency_user.repository;


import com.sparta.currency_user.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {


    List<Exchange> findByUserId(Long userId);
}
