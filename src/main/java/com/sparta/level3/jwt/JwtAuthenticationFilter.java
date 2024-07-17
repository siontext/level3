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

// 요청을 가로채서 JWT를 검증하는 필터
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends HttpFilter {

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

        // 인증이 필요 없는 URL은 필터를 통과시킴
        if (EXCLUDE_URLS.contains(requestURI)) {
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

            // 역할 검증 (JWT 토큰에 역할 정보가 포함되어 있어야 함)
            String role = jwtUtil.extractRole(jwt); // JWT 토큰에서 역할을 추출하는 메서드를 추가해야 함
            request.setAttribute("role", role); // 역할 정보를 추가
            chain.doFilter(request, response); // 모든 인증된 사용자는 요청을 계속 처리
        } else {
            // 인증되지 않은 경우 401 상태 코드를 설정
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
