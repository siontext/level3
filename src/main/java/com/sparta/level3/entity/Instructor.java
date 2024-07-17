package com.sparta.level3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;


//강사 엔티티
@Entity
@Getter
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int experience; //경력

    private String company; //회사

    private String phone; //전화번호

    private String introduction; //소개


    //JPA 사용을 위한 기본생성자
    public Instructor() {
    }

    //생성자
    public Instructor(String name, int experience, String company, String phone, String introduction) {
        this.name = name;
        this.experience = experience;
        this.company = company;
        this.phone = phone;
        this.introduction = introduction;
    }
}
