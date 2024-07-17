package com.sparta.level3.controller;

import com.sparta.level3.dto.AdminRequestDto;
import com.sparta.level3.dto.LoginRequestDto;
import com.sparta.level3.dto.LoginResponseDto;
import com.sparta.level3.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    //생성자 주입
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    // 관리자 회원가입 엔드포인트
    @PostMapping("/join")
    public ResponseEntity<String> joinAdmin(@Validated @RequestBody AdminRequestDto requestDto) {
        try {
            adminService.joinAdmin(requestDto);
            return new ResponseEntity<>("관리자 가입 성공", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //서비스단에서 작성했던 RuntimeException메시지들을 클라이언트에게 반환
        }
    }

    // 관리자 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDto requestDto) {
        try {
            LoginResponseDto responseDto = adminService.login(requestDto);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + responseDto.getToken())
                    .body(responseDto);//반환타입 responseDto
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//e.getMessage() 반환타입 String -> 반환타입이 고정되어있지 않아서 제네릭 사용
        }
    }
}