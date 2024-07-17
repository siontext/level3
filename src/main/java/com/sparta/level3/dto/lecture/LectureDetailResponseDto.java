package com.sparta.level3.dto.lecture;

import com.sparta.level3.enums.Category;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LectureDetailResponseDto {
    private Long id;
    private String title;
    private int price;
    private String description;
    private Category category;
    private String teacherName;
    private LocalDate registrationDate;

    public LectureDetailResponseDto(Long id, String title, int price, String description, Category category, String teacherName, LocalDate registrationDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.teacherName = teacherName;
        this.registrationDate = registrationDate;
    }
}