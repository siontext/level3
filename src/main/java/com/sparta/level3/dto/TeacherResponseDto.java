package com.sparta.level3.dto;

import lombok.Getter;

@Getter
public class TeacherResponseDto {

    private Long id;
    private String name;
    private int experience; // 경력
    private String company;
    private String phone;
    private String introduction;


    public TeacherResponseDto(Long id, String name, int experience, String company, String phone, String introduction) {
        this.id = id;
        this.name = name;
        this.experience = experience;
        this.company = company;
        this.phone = phone;
        this.introduction = introduction;
    }
}
