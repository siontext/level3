package com.sparta.level3.service;

import com.sparta.level3.config.JwtUtil;
import com.sparta.level3.dto.AdminRequestDto;
import com.sparta.level3.dto.LoginRequestDto;
import com.sparta.level3.dto.LoginResponseDto;
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
    private final JwtUtil jwtUtil;

    //생성자 주입
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    //관리자 회원가입
    //클라이언트에게 반환할 데이터가 없기 때문에 걍 엔티티로 반환타입 처리하자
    public Admin joinAdmin(AdminRequestDto requestDto) {

        //이메일 중복인지 확인
        if (adminRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        //마케팅 부서는 -> 매니저 권한을 부여받을 수 없음 예외처리하기
        if (requestDto.getRole() == Role.MANAGER && requestDto.getDepartment() == Department.MARKETING) {
            throw new RuntimeException("마케팅 부서는 MANAGER 권한을 부여받을 수 없습니다.");
        }

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        //admin객체 생성
        Admin admin = new Admin(
                requestDto.getEmail(),
                encodedPassword,
                requestDto.getDepartment(),
                requestDto.getRole()

        );

        //어드민 객체 DB에 저장후 반환
        return adminRepository.save(admin);

    }


    //관리자 로그인
    public LoginResponseDto login(LoginRequestDto requestDto) {

        //이메일 있는지 확인
        Admin admin = adminRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("잘못된 이메일 입니다."));

        //비밀번호 확인 (들어온 비번과 로그인 하려는 이메일의 비번)
        if (!passwordEncoder.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호 입니다.");
        }

        //토큰 생성하기
        String token = jwtUtil.createToken(admin.getEmail());

        //이메일과 토큰을 반환
        return new LoginResponseDto(admin.getEmail(), token);
    }





}
