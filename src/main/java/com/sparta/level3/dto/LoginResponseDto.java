package com.sparta.level3.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String email;

    //로그인 할때 -> 토큰도 함께 보내줌 그래야 다음번에 토큰을 주겠지?
    private String token;


    //생성자
    public LoginResponseDto(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
