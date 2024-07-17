package com.sparta.level3.dto.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminResponseDto {
    private String message;
    private Long adminId;

    public AdminResponseDto(String message, Long adminId) {
        this.message = message;
        this.adminId = adminId;
    }
}
