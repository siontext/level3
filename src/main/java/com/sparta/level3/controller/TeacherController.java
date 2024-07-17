package com.sparta.level3.controller;

import com.sparta.level3.dto.teacher.TeacherDetailResponseDto;
import com.sparta.level3.dto.teacher.TeacherRequestDto;
import com.sparta.level3.dto.teacher.TeacherResponseDto;
import com.sparta.level3.service.TeacherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    // 생성자 주입
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * 강사 등록 메서드
     *
     * @param requestDto 강사 등록 요청 DTO
     * @param request    HttpServletRequest 객체
     * @return 등록된 강사의 정보가 담긴 ResponseEntity 객체
     */
    @PostMapping
    public ResponseEntity<TeacherResponseDto> addTeacher(@RequestBody TeacherRequestDto requestDto, HttpServletRequest request) {
        // 역할 검증
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        TeacherResponseDto responseDto = teacherService.addTeachers(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    /**
     * 강사 정보 수정 메서드
     *
     * @param id         강사 ID
     * @param requestDto 강사 정보 수정 요청 DTO
     * @param request    HttpServletRequest 객체
     * @return 수정된 강사의 정보가 담긴 ResponseEntity 객체
     */
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDto requestDto, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        TeacherResponseDto responseDto = teacherService.updateTeacher(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    /**
     * 선택한 강사 조회 메서드
     * @param id 선택한 강사의 ID (PathVariable로 받아옴)
     * @param request HttpServletRequest 객체 (헤더에서 JWT 토큰을 받아오기 위함)
     * @return 선택한 강사의 정보가 담긴 ResponseEntity 객체
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDetailResponseDto> getTeacherDetail(@PathVariable Long id, HttpServletRequest request) {
        // 역할 검증 (JWT 토큰에서 역할 정보 추출)
        String role = (String) request.getAttribute("role");
        if (!"MANAGER".equals(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        TeacherDetailResponseDto responseDto = teacherService.getTeacherDetail(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
