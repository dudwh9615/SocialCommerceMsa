package Project.SocialCommerce.service;

import Project.SocialCommerce.controller.ActivityClient;
import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.User;
import Project.SocialCommerce.repository.UserRepository;
import Project.SocialCommerce.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ActivityClient activityClient;

    public void register(RegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        requestDto.setPwd(passwordEncoder.encode(requestDto.getPwd()));

        // 회원 중복 확인
        Optional<User> checkDuplicate = userRepository.findByEmail(email);
        if (checkDuplicate.isPresent()){
            throw new IllegalArgumentException("중복 이메일 입니다.");
        }
        // 유저 등록
        User saved = userRepository.save(requestDto.toEntity());
        activityClient.createFollowInfo(saved.getId());
    }

    public UserResponseDto findByEmail(String email) {
        Optional<User> res = userRepository.findByEmail(email);
        if (res.isEmpty()) {
            throw new IllegalArgumentException("등록된 이메일이 없습니다.");
        }

        return UserResponseDto.toDto(res.get());
    }

    public UserResponseDto findByJwt(String jwt) {
        jwt = jwtUtil.substringToken(jwt);
        Claims userInfo = jwtUtil.getUserInfoFromToken(jwt);
        String email = userInfo.getSubject();

        return findByEmail(email);
    }

    public void modifyUserInfo(ModifyRequestDto modifyRequestDto, String email) {
        Optional<User> originalUserOptional = userRepository.findByEmail(email);
        if (originalUserOptional.isEmpty()) {
            throw new IllegalArgumentException("계정 정보가 없습니다");
        }
        User originalUser = originalUserOptional.get();

        // 바뀐 내용이 있다면 originalUser 갱신
        if (modifyRequestDto.getName() != null) {
            originalUser.setName(modifyRequestDto.getName());
        }
        if (modifyRequestDto.getProfile() != null) {
            originalUser.setProfile(modifyRequestDto.getProfile());
        }
        if (modifyRequestDto.getGreetings() != null) {
            originalUser.setGreetings(modifyRequestDto.getGreetings());
        }
        // 비밀번호의 경우 변경이 되었다면 쿠키의 토큰 만료하여 로그아웃처리
        if (modifyRequestDto.getPwd() != null) {
            if (!originalUser.getPwd().equals(passwordEncoder.encode(modifyRequestDto.getPwd()))) {
                originalUser.setPwd(passwordEncoder.encode(modifyRequestDto.getPwd()));
            }
        }

        userRepository.save(originalUser);
    }
}
