package com.sparta.level3.controller;

import com.sparta.level3.dto.AdminRequestDto;
import com.sparta.level3.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
