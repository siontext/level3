package com.sparta.level3.controller;

import com.sparta.level3.dto.lecture.*;
import com.sparta.level3.enums.Category;
import com.sparta.level3.jwt.JwtUtil;
import com.sparta.level3.service.LectureService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    /**
     * 선택한 강의 조회 메서드
     * @param id 선택한 강의의 ID (PathVariable로 받아옴)
     * @param request HttpServletRequest 객체 (헤더에서 JWT 토큰을 받아오기 위함)
     * @return 선택한 강의의 정보가 담긴 ResponseEntity 객체
     */
    @GetMapping("/{id}")
    public ResponseEntity<LectureDetailResponseDto> getLectureDetail(@PathVariable Long id, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        LectureDetailResponseDto responseDto = lectureService.getLectureDetail(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



    /**
     * 선택한 강사가 촬영한 강의 목록 조회 메서드
     * @param teacherId 선택한 강사의 ID (PathVariable로 받아옴)
     * @param request HttpServletRequest 객체 (헤더에서 JWT 토큰을 받아오기 위함)
     * @return 선택한 강사가 촬영한 강의 목록이 담긴 ResponseEntity 객체
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<LectureSummaryResponseDto>> getLecturesByTeacher(@PathVariable Long teacherId, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<LectureSummaryResponseDto> responseDtoList = lectureService.getLecturesByTeacher(teacherId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }


    /**
     * 카테고리별 강의 목록 조회 메서드
     * @param category 선택한 카테고리 (PathVariable로 받아옴)
     * @param request HttpServletRequest 객체 (헤더에서 JWT 토큰을 받아오기 위함)
     * @return 선택한 카테고리에 포함된 강의 목록이 담긴 ResponseEntity 객체
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<LectureCategoryResponseDto>> getLecturesByCategory(@PathVariable Category category, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<LectureCategoryResponseDto> responseDtoList = lectureService.getLecturesByCategory(category);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
}
