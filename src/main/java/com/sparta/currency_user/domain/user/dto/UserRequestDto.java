package com.sparta.currency_user.domain.user.dto;

import com.sparta.currency_user.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank(message = "사용자 이름은 비워둘 수 없습니다.")
    @Size(max = 10, message = "사용자 이름은 10자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    public User toEntity() {
        return new User(
                this.name,
                this.email
        );
    }
}
