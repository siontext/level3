package com.sparta.level3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Spring의 설정 클래스를 의미
@EnableWebSecurity // Spring Security의 웹 보안을 활성화
public class SecurityConfig {

    //PasswordEncoder 스프링빈에 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //패스워트 암호화 타입
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(authorize -> authorize // HTTP 요청에 대한 인가(authorization) 규칙을 설정
                        .requestMatchers("/api/admin/**").permitAll() // /api/admin 엔드포인트 요청은 인증없이 허용
                        .anyRequest().authenticated() // 다른 모든 요청은 인증 필요
                );

        return http.build(); //설정된 HttpSecurity 객체를 기반으로 SecurityFilterChain을 빌드하여 반환
    }
}
