package com.sparta.level3.service;

import com.sparta.level3.dto.AdminRequestDto;
import com.sparta.level3.dto.AdminResponseDto;
import com.sparta.level3.dto.LoginRequestDto;
import com.sparta.level3.dto.LoginResponseDto;
import com.sparta.level3.entity.Admin;
import com.sparta.level3.entity.Department;
import com.sparta.level3.entity.Role;
import com.sparta.level3.jwt.JwtUtil;
import com.sparta.level3.repository.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //생성자 주입
    public AdminService(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }


    // 관리자 회원가입
    public AdminResponseDto joinAdmin(AdminRequestDto requestDto) {

        // 이메일 중복인지 확인
        if (adminRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 마케팅 부서는 MANAGER 권한을 부여받을 수 없음 예외처리하기
        if (requestDto.getRole() == Role.MANAGER && requestDto.getDepartment() == Department.MARKETING) {
            throw new RuntimeException("마케팅 부서는 MANAGER 권한을 부여받을 수 없습니다.");
        }

        // 비밀번호 형식 검증
        if (!isValidPassword(requestDto.getPassword())) {//isValidPassword 메서드를 추가하여 비밀번호 형식을 검증
            throw new RuntimeException("비밀번호는 최소 8자 이상, 최대 15자 이하이며 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // Admin 객체 생성
        Admin admin = new Admin(
                requestDto.getEmail(),
                encodedPassword,
                requestDto.getDepartment(),
                requestDto.getRole()
        );

        // Admin 객체를 DB에 저장
        Admin savedAdmin = adminRepository.save(admin);

        // AdminResponseDto 반환
        return new AdminResponseDto("관리자가 성공적으로 등록되었습니다.", savedAdmin.getId());
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

        //LoginResponseDto 반환
        return new LoginResponseDto(
                "로그인에 성공하였습니다.",
                admin.getId(),
                admin.getEmail(),
                admin.getDepartment(),
                admin.getRole(),
                token
        );
    }






    // 비밀번호 형식 검증 메서드
    private boolean isValidPassword(String password) {
        // 최소 8자, 최대 15자, 알파벳 대소문자, 숫자, 특수문자 포함 정규식
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?]).{8,15}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
