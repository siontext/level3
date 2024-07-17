package com.sparta.level3.dto;


import lombok.Getter;

//강사 등록 요청 Dto
@Getter
public class TeacherRequestDto {

    private String name;

    private int experience; //경력

    private String company; //회서

    private String phone; //전화번호

    private String introduction; //소개
}
