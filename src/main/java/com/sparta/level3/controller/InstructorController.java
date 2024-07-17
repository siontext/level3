package com.sparta.level3.controller;

import com.sparta.level3.jwt.JwtUtil;
import com.sparta.level3.dto.InstructorRequestDto;
import com.sparta.level3.dto.InstructorResponseDto;
import com.sparta.level3.service.InstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;
    private final JwtUtil jwtUtil;

    public InstructorController(InstructorService instructorService, JwtUtil jwtUtil) {
        this.instructorService = instructorService;
        this.jwtUtil = jwtUtil;
    }


    //강사등록(저장) 컨트롤러
    @PostMapping
    public ResponseEntity<InstructorResponseDto> addInstructor(@RequestHeader String token,
                                                               @Valid @RequestBody InstructorRequestDto requestDto,
                                                               @AuthenticationPrincipal UserDetails userDetails) {

        //토큰이 알맞은지 확인?
        String email = jwtUtil.extractEmail(token.replace("Bearer ", "")); //JWT 구분자 Bearer를 잘라줘야 순수한 JWT토큰이된다.
        if (userDetails == null || !userDetails.getUsername().equals(email)) { //userDetails가 토큰 정보를 넣는곳이야? 틀리면 주석 수정좀 해줘
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //코드 설명좀해줘
        }

        //강사 등록
        InstructorResponseDto responseDto = instructorService.addInstructor(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
