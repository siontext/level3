package com.sparta.level3.dto.lecture;

import com.sparta.level3.enums.Category;
import lombok.Getter;

@Getter
public class LectureUpdateRequestDto {

    private String title;
    private int price;
    private String description; //강의 설명
    private Category category;
}
