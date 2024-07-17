package com.sparta.level3.entity;

import com.sparta.level3.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

//    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 최대 15자 이하로 입력하세요.") 인코딩해서 들어오기 때문에 60자이상임
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) //STRING으로 써야지 한글로 출력됨
    private Department department;

    @Enumerated(EnumType.STRING)
    private Role role;

    //JPA사용을 위한 기본 생성자
    public Admin() {
    }



    public Admin(String email, String encodedPassword, Department department, Role role) {
        this.email = email;
        this.password = encodedPassword;
        this.department = department;
        this.role = role;
    }
}
