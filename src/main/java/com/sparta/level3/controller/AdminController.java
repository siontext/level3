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


    @PostMapping
    public ResponseEntity<String> joinAdmin(@Validated @RequestBody AdminRequestDto requestDto) {
        try {
            adminService.joinAdmin(requestDto);
            return new ResponseEntity<>("관리자 가입 성공", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Validated @RequestBody LoginRequestDto requestDto) {

        try {
            LoginResponseDto responseDto = adminService.login(requestDto); //로그인 시도 성공시
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + responseDto.getToken())
                    .body(responseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); //로그인 실패시
        }
    }
}
