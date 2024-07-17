package com.sparta.level3.dto.lecture;

import com.sparta.level3.enums.Category;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LectureSummaryResponseDto {
    private Long id;
    private String title;
    private int price;
    private String description;
    private Category category;
    private LocalDate registrationDate;

    public LectureSummaryResponseDto(Long id, String title, int price, String description, Category category, LocalDate registrationDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.registrationDate = registrationDate;
    }
}