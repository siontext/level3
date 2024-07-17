package com.sparta.level3.service;

import com.sparta.level3.dto.teacher.TeacherDetailResponseDto;
import com.sparta.level3.dto.teacher.TeacherRequestDto;
import com.sparta.level3.dto.teacher.TeacherResponseDto;
import com.sparta.level3.entity.Teacher;
import com.sparta.level3.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    //강사등록(저장) 서비스
    public TeacherResponseDto addTeachers(TeacherRequestDto requestDto) {

        //강사 객체 생성
        Teacher teacher = new Teacher(
                requestDto.getName(),
                requestDto.getExperience(),
                requestDto.getCompany(),
                requestDto.getPhone(),
                requestDto.getIntroduction()
        );

        //강사객체 저장
        Teacher savedTeacher = teacherRepository.save(teacher);

        //강사객체를 -> DTO로 만들기
        TeacherResponseDto responseDto = new TeacherResponseDto(
                savedTeacher.getId(),
                savedTeacher.getName(),
                savedTeacher.getExperience(),
                savedTeacher.getCompany(),
                savedTeacher.getPhone(),
                savedTeacher.getIntroduction()
        );

        //DTO객체 반환
        return responseDto;
    }


    // 강사 정보 수정 서비스
    @Transactional
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto requestDto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 강사를 찾을 수 없습니다."));

        // 강사 정보 업데이트
        teacher.update(requestDto.getExperience(), requestDto.getCompany(), requestDto.getPhone(), requestDto.getIntroduction());

        return new TeacherResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getExperience(),
                teacher.getCompany(),
                teacher.getPhone(),
                teacher.getIntroduction()
        );
    }

    /**
     * 선택한 강사 조회 서비스 메서드
     * @param id 선택한 강사의 ID
     * @return 선택한 강사 정보가 담긴 DTO
     */
    public TeacherDetailResponseDto getTeacherDetail(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 강사를 찾을 수 없습니다."));

        return new TeacherDetailResponseDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getExperience(),
                teacher.getCompany(),
                teacher.getPhone(),
                teacher.getIntroduction()
        );
    }
}