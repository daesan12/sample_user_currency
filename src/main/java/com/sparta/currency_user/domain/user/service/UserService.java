package com.sparta.currency_user.domain.user.service;

import com.sparta.currency_user.domain.user.dto.UserRequestDto;
import com.sparta.currency_user.domain.user.dto.UserResponseDto;
import com.sparta.currency_user.domain.user.entity.User;
import com.sparta.currency_user.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 특정 사용자 조회
     * @param id 사용자 ID
     * @return 사용자 응답 DTO
     */
    public UserResponseDto findById(Long id) {
        User user = findUserById(id);
        return UserResponseDto.toDto(findUserById(id));
    }

    /**
     * 사용자 ID로 사용자 엔티티 조회
     * @param id 사용자 ID
     * @return 사용자 엔티티
     */
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 모든 사용자 조회
     * @return 사용자 응답 DTO 리스트
     */

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    /**
     * 사용자 저장
     * @param userRequestDto 사용자 생성 요청 DTO
     * @return 저장된 사용자 응답 DTO
     */
    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User savedUser = userRepository.save(userRequestDto.toEntity());
        return UserResponseDto.toDto(savedUser);
    }

    /**
     * 사용자 삭제
     * @param id 사용자 ID
     */
    @Transactional
    public void deleteUserById(Long id) {
        this.findUserById(id);
        userRepository.deleteById(id);
    }

}
