package com.sparta.level3.entity;

import com.sparta.level3.dto.AdminRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "올바른 이메일 형식을 입력하세요.") //이메일 형식 검증
    @Column(nullable = false) //notNull 설정
    private String email;

    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 최대 15자 이하로 입력하세요.")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //STRING으로 써야지 한글로 출력됨
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    //생성자 AdminRequestDto에서 받아온 데이터로 생성
    public Admin(AdminRequestDto adminRequestDto) {
        this.email = adminRequestDto.getEmail();
        this.password = adminRequestDto.getPassword();
        this.department = adminRequestDto.getDepartment();
        this.role = adminRequestDto.getRole();
    }

    //JPA사용을 위한 기본 생성자
    public Admin() {
    }
}
