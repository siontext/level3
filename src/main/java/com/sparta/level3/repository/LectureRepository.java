package com.sparta.level3.repository;

import com.sparta.level3.entity.Lecture;
import com.sparta.level3.entity.Teacher;
import com.sparta.level3.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    List<Lecture> findAllByTeacherOrderByRegistrationDateDesc(Teacher teacher);

    List<Lecture> findAllByCategoryOrderByRegistrationDateDesc(Category category);


}
