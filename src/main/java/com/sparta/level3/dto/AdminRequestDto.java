package com.sparta.level3.dto;

import com.sparta.level3.entity.Department;
import com.sparta.level3.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminRequestDto {

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 최소 8자 이상, 최대 15자 이하로 입력하세요.")
    private String password;

    private Department department; //부서이름

    private Role role; //역할

    //생성자
    public AdminRequestDto(String email, String password, Department department, Role role) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }
}
