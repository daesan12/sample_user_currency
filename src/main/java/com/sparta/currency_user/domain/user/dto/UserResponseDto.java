package com.sparta.currency_user.domain.user.dto;

import com.sparta.currency_user.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;

    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
