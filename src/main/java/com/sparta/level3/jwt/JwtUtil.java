package com.sparta.level3.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

// JWT를 생성하고 검증하는 유틸리티 클래스
@Component // 스프링 빈으로 등록
public class JwtUtil {

    // Base64로 인코딩된 256비트 키 설정
    private final String SECRET_KEY = "aGVsbG9oZWxsb2hlbGxvaGVsbG9oZWxsb2hlbGxvaGVsbG9oZWxsb2g"; // 256비트(32바이트) 길이
    private final SecretKey secretKey; // SecretKey 객체

    // 생성자에서 시크릿 키를 설정
    public JwtUtil() {
        // Base64로 인코딩된 시크릿 키를 디코딩하여 SecretKey 객체 생성
        this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * JWT 토큰 생성 메서드
     * @param email 사용자의 이메일을 토큰에 포함
     * @return 생성된 JWT 토큰
     */
    public String createToken(String email) {
        // JWT 빌더를 사용하여 토큰 생성
        return Jwts.builder()
                .setSubject(email) // 이메일을 주제로 설정
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정 (현재 시간)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 만료 시간 설정 (현재 시간으로부터 10시간 후)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘 및 비밀 키 설정 (HS256 알고리즘 사용)
                .compact(); // 토큰 생성
    }

    /**
     * JWT 토큰 검증 메서드
     * @param token JWT 토큰
     * @param email 사용자 이메일
     * @return 토큰이 유효한지 여부
     */
    public boolean validateToken(String token, String email) {
        // 토큰에서 이메일 추출
        final String extractedEmail = extractEmail(token);
        // 추출한 이메일과 사용자 이메일이 일치하고, 토큰이 만료되지 않았는지 확인
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    /**
     * JWT 토큰에서 이메일 추출 메서드
     * @param token JWT 토큰
     * @return 토큰에 포함된 이메일
     */
    public String extractEmail(String token) {
        // 토큰을 파싱하고 클레임(Claims)을 추출하여 주제(subject)를 반환 (이메일을 주제로 사용)
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject(); // 이메일 반환
    }

    /**
     * JWT 토큰 만료 여부 확인 메서드
     * @param token JWT 토큰
     * @return 토큰이 만료되었는지 여부
     */
    private boolean isTokenExpired(String token) {
        // 토큰에서 만료 시간을 추출하여 현재 시간과 비교
        final Date expiration = extractExpiration(token);
        // 만료 시간이 현재 시간보다 이전이면 true 반환 (즉, 만료된 경우)
        return expiration.before(new Date());
    }

    /**
     * JWT 토큰에서 만료 시간 추출 메서드
     * @param token JWT 토큰
     * @return 토큰의 만료 시간
     */
    private Date extractExpiration(String token) {
        // 토큰을 파싱하고 클레임(Claims)을 추출하여 만료 시간을 반환
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration(); // 만료 시간 반환
    }
}
