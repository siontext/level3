package com.sparta.level3.service;

import com.sparta.level3.dto.InstructorRequestDto;
import com.sparta.level3.dto.InstructorResponseDto;
import com.sparta.level3.entity.Instructor;
import com.sparta.level3.repository.InstructorRepository;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }


    //강사등록(저장) 서비스
    public InstructorResponseDto addInstructor(InstructorRequestDto requestDto) {

        //강사 객체 생성
        Instructor instructor = new Instructor(
                requestDto.getName(),
                requestDto.getExperience(),
                requestDto.getCompany(),
                requestDto.getPhone(),
                requestDto.getIntroduction()
        );

        //강사객체 저장
        Instructor savedInstructor = instructorRepository.save(instructor);

        //강사객체를 -> DTO로 만들기
        InstructorResponseDto responseDto = new InstructorResponseDto(
                savedInstructor.getId(),
                savedInstructor.getName(),
                savedInstructor.getExperience(),
                savedInstructor.getCompany(),
                savedInstructor.getPhone(),
                savedInstructor.getIntroduction()
        );

        //DTO객체 반환
        return responseDto;
    }

}
