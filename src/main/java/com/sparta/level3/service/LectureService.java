package com.sparta.level3.service;

import com.sparta.level3.dto.lecture.*;
import com.sparta.level3.entity.Lecture;
import com.sparta.level3.entity.Teacher;
import com.sparta.level3.enums.Category;
import com.sparta.level3.repository.LectureRepository;
import com.sparta.level3.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 선택한 강의 조회 서비스 메서드
     * @param id 선택한 강의의 ID
     * @return 선택한 강의 정보가 담긴 DTO
     */
    public LectureDetailResponseDto getLectureDetail(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 강의를 찾을 수 없습니다."));

        return new LectureDetailResponseDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getPrice(),
                lecture.getDescription(),
                lecture.getCategory(),
                lecture.getTeacher().getName(),
                lecture.getRegistrationDate()
        );
    }


    /**
     * 선택한 강사가 촬영한 강의 목록 조회 서비스 메서드
     * @param teacherId 선택한 강사의 ID
     * @return 선택한 강사가 촬영한 강의 목록
     */
    public List<LectureSummaryResponseDto> getLecturesByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("해당 강사를 찾을 수 없습니다."));

        List<Lecture> lectures = lectureRepository.findAllByTeacherOrderByRegistrationDateDesc(teacher);

        return lectures.stream()
                .map(lecture -> new LectureSummaryResponseDto(//스트림 배열로 선언하기
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getPrice(),
                        lecture.getDescription(),
                        lecture.getCategory(),
                        lecture.getRegistrationDate()
                ))
                .collect(Collectors.toList()); //리스트로 선언
    }

    /**
     * 카테고리별 강의 목록 조회 서비스 메서드
     * @param category 선택한 카테고리
     * @return 선택한 카테고리에 포함된 강의 목록
     */
    @Transactional(readOnly = true)
    public List<LectureCategoryResponseDto> getLecturesByCategory(Category category) {
        List<Lecture> lectures = lectureRepository.findAllByCategoryOrderByRegistrationDateDesc(category);

        return lectures.stream() //스트림 배열로 생성
                .map(lecture -> new LectureCategoryResponseDto(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getPrice(),
                        lecture.getDescription(),
                        lecture.getCategory(),
                        lecture.getTeacher().getName(),
                        lecture.getRegistrationDate()
                ))
                .collect(Collectors.toList());//리스트로 반환
    }
}


