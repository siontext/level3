package com.sparta.level3.controller;

import com.sparta.level3.dto.LectureRequestDto;
import com.sparta.level3.dto.LectureResponseDto;
import com.sparta.level3.dto.LectureUpdateRequestDto;
import com.sparta.level3.dto.LectureUpdateResponseDto;
import com.sparta.level3.jwt.JwtUtil;
import com.sparta.level3.service.LectureService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;
    private final JwtUtil jwtUtil;

    public LectureController(LectureService lectureService, JwtUtil jwtUtil) {
        this.lectureService = lectureService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 강의 등록 메서드
     * @param requestDto 강의 등록 요청 DTO
     * @param request HttpServletRequest 객체
     * @return 등록된 강의의 정보가 담긴 ResponseEntity 객체
     */
    @PostMapping
    public ResponseEntity<LectureResponseDto> addLecture(@RequestBody LectureRequestDto requestDto, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LectureResponseDto responseDto = lectureService.addLecture(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }



    /**
     * 강의 정보 수정 메서드
     * @param id 수정할 강의 ID
     * @param requestDto 강의 수정 요청 DTO
     * @param request HttpServletRequest 객체
     * @return 수정된 강의의 정보가 담긴 ResponseEntity 객체
     */
    @PutMapping("/{id}")
    public ResponseEntity<LectureUpdateResponseDto> updateLecture(@PathVariable Long id, @RequestBody LectureUpdateRequestDto requestDto, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LectureUpdateResponseDto responseDto = lectureService.updateLecture(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}