package com.sparta.level3.dto.login;

import com.sparta.level3.entity.Department;
import com.sparta.level3.enums.Role;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String message;
    private Long id;
    private String email;
    private Department department;
    private Role role;
    private String token;   //로그인 할때 -> 토큰도 함께 보내줌 그래야 다음번에 토큰을 주겠지?

    public LoginResponseDto(String message, Long id, String email, Department department, Role role, String token) {
        this.message = message;
        this.id = id;
        this.email = email;
        this.department = department;
        this.role = role;
        this.token = token;
    }
}