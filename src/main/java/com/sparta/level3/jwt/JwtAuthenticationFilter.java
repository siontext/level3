package com.sparta.level3.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

//요청을 가로채서 JWT를 검증하는 필터
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends HttpFilter  { //HttpFilter를 사용하는 경우 특정 컨텍스트에서만 사용

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());//로그


    private final JwtUtil jwtUtil;


    // 인증이 필요 없는 URL 패턴 설정하기
    private static final List<String> EXCLUDE_URLS = List.of(
            "/api/admin/join",
            "/api/admin/login"
    );


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        String requestURI = request.getRequestURI();
        logger.info("Request URI: " + requestURI);// 로그


        // 인증이 필요 없는 URL은 필터를 통과시킴
        if (EXCLUDE_URLS.contains(requestURI)) {
            logger.info("Excluded URL, passing through: " + requestURI);//로그
            chain.doFilter(request, response);
            return;
        }


        // 요청 헤더에서 Authorization 헤더 값을 가져옴
        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        // Authorization 헤더가 존재하고 "Bearer "로 시작하는 경우
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer "를 제거하고 JWT 토큰을 가져옴
            email = jwtUtil.extractEmail(jwt); // JWT 토큰에서 이메일을 추출
        }

        // 이메일이 존재하고 JWT 토큰이 유효한 경우
        if (email != null && jwtUtil.validateToken(jwt, email)) {
            // 요청에 이메일 속성을 추가
            request.setAttribute("email", email);
        } else {
            // 인증되지 않은 경우 401 상태 코드를 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 다음 필터 체인을 호출하여 요청을 처리
        chain.doFilter(request, response);
    }
}