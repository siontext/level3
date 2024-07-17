package com.sparta.level3.service;

import com.sparta.level3.dto.LectureRequestDto;
import com.sparta.level3.dto.LectureResponseDto;
import com.sparta.level3.dto.LectureUpdateRequestDto;
import com.sparta.level3.dto.LectureUpdateResponseDto;
import com.sparta.level3.entity.Lecture;
import com.sparta.level3.entity.Teacher;
import com.sparta.level3.repository.LectureRepository;
import com.sparta.level3.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final TeacherRepository teacherRepository;

    // 생성자를 통해 LectureRepository와 TeacherRepository 주입
    public LectureService(LectureRepository lectureRepository, TeacherRepository teacherRepository) {
        this.lectureRepository = lectureRepository;
        this.teacherRepository = teacherRepository;
    }


    //강의 등록 메서드
    @Transactional
    public LectureResponseDto addLecture(LectureRequestDto requestDto) {
        // 요청받은 강사 ID로 강사 정보를 조회
        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("해당 강사를 찾을 수 없습니다."));

        // 새로운 강의 객체 생성
        Lecture lecture = new Lecture(
                requestDto.getTitle(),
                requestDto.getPrice(),
                requestDto.getDescription(),
                requestDto.getCategory(),
                teacher,
                LocalDate.now() // 현재 날짜를 등록일로 설정
        );

        // 강의 객체를 데이터베이스에 저장
        Lecture savedLecture = lectureRepository.save(lecture);

        // 저장된 강의 정보를 LectureResponseDto로 변환하여 반환
        return new LectureResponseDto(
                savedLecture.getId(),
                savedLecture.getTitle(),
                savedLecture.getPrice(),
                savedLecture.getDescription(),
                savedLecture.getCategory(),
                savedLecture.getTeacher().getName(),
                savedLecture.getRegistrationDate()
        );
    }


    /**
     * 강의 정보 수정 서비스
     * @param id 수정할 강의 ID
     * @param requestDto 강의 수정 요청 DTO
     * @return 수정된 강의 정보를 담은 DTO
     */
    @Transactional
    public LectureUpdateResponseDto updateLecture(Long id, LectureUpdateRequestDto requestDto) {
        // 선택한 강의를 ID로 조회
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 강의를 찾을 수 없습니다."));

        // 강의 정보 업데이트
        lecture.update(requestDto);

        // 업데이트된 강의 정보를 DTO로 변환하여 반환
        return new LectureUpdateResponseDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getPrice(),
                lecture.getDescription(),
                lecture.getCategory(),
                lecture.getTeacher().getName(),
                lecture.getRegistrationDate()
        );
    }
}

