package com.sparta.level3.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

//로그인 요청 및 응답 DTO
@Getter
public class LoginRequestDto {

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;


    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
