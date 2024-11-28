package com.sparta.currency_user.domain.exchange.repository;


import com.sparta.currency_user.domain.exchange.dto.FindGroupResponseDto;
import com.sparta.currency_user.domain.exchange.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {


    List<Exchange> findByUserId(Long userId);


    @Query("SELECT new com.sparta.currency_user.domain.exchange.dto.FindGroupResponseDto(e.user.id, COUNT(e), SUM(e.amountInKrw)) "+
            "FROM Exchange e "+
            "GROUP BY e.user.id")

    List<FindGroupResponseDto> findGroup();
}
