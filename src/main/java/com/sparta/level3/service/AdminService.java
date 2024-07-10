package com.sparta.level3.service;

import com.sparta.level3.dto.AdminRequestDto;
import com.sparta.level3.entity.Admin;
import com.sparta.level3.entity.Department;
import com.sparta.level3.entity.Role;
import com.sparta.level3.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    //생성자 주입
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //클라이언트에게 반환할 데이터가 없기 때문에 걍 엔티티로 반환타입 처리하자
    public Admin joinAdmin(AdminRequestDto requestDto) {

        //admin객체 생성 (requestDto데이터로)
        Admin admin = new Admin(requestDto);

        //이메일 중복인지 확인
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        //마케팅 부서는 -> 매니저 권한을 부여받을 수 없음 예외처리하기
        if (admin.getRole() == Role.MANAGER && requestDto.getDepartment() == Department.MARKETING) {
            throw new RuntimeException("마케팅 부서는 MANAGER 권한을 부여받을 수 없습니다.");
        }

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        //어드민 DB에 저장후 반환
        return adminRepository.save(admin);

    }




}
