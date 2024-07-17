package com.sparta.level3.dto;

import com.sparta.level3.enums.Category;
import lombok.Getter;

@Getter
public class LectureRequestDto {
    private String title;
    private int price;
    private String description;
    private Category category;
    private Long teacherId; // Teacher ID를 받아와서 Lecture와 연관시킵니다.
}