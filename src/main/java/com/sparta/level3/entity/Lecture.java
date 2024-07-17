package com.sparta.level3.entity;

import com.sparta.level3.dto.LectureRequestDto;
import com.sparta.level3.dto.LectureUpdateRequestDto;
import com.sparta.level3.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
public class Lecture { //강의 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //강의명

    private int price; //가격

    private String description; //소개

    @Enumerated(EnumType.STRING)
    private Category category; //카테고리

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; //강사

    private LocalDate registrationDate; //등록일


    public Lecture(String title, int price, String description, Category category, Teacher teacher, LocalDate registrationDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.teacher = teacher;
        this.registrationDate = registrationDate;
    }

    // 강의 정보 업데이트 메서드
    public void update(LectureUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.category = requestDto.getCategory();
    }

    public Lecture() {
    }


}