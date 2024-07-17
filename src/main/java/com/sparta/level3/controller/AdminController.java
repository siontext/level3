package com.sparta.level3.controller;

import com.sparta.level3.dto.admin.AdminRequestDto;
import com.sparta.level3.dto.login.LoginRequestDto;
import com.sparta.level3.dto.login.LoginResponseDto;
import com.sparta.level3.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    //생성자 주입을 통한 의존성 주입
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 관리자 회원가입 엔드포인트
     * @param requestDto 관리자 회원가입 요청 DTO
     * @return 회원가입 성공 메시지 또는 에러 메시지와 함께 ResponseEntity 객체
     */
    @PostMapping("/join")
    public ResponseEntity<String> joinAdmin(@Validated @RequestBody AdminRequestDto requestDto) {
        try {
            adminService.joinAdmin(requestDto);
            return new ResponseEntity<>("관리자 가입 성공", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 서비스단에서 발생한 RuntimeException 메시지를 클라이언트에게 반환
        }
    }

    /**
     * 관리자 로그인 엔드포인트
     * @param requestDto 관리자 로그인 요청 DTO
     * @return 로그인 성공 시 JWT 토큰을 포함한 ResponseEntity 객체 또는 에러 메시지
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDto requestDto) {
        try {
            LoginResponseDto responseDto = adminService.login(requestDto);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + responseDto.getToken()) // 응답 헤더에 JWT 토큰 추가
                    .body(responseDto); // 로그인 응답 DTO를 반환
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 서비스단에서 발생한 RuntimeException 메시지를 클라이언트에게 반환
        }
    }
}
