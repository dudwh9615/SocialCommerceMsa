package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.model.User;
import Project.SocialCommerce.repository.UserRepository;
import Project.SocialCommerce.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
//    private final ActivityRepository activityRepository;



    public void register(RegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        requestDto.setPwd(passwordEncoder.encode(requestDto.getPwd()));

        // 회원 중복 확인
        Optional<User> checkDuplicate = userRepository.findByEmail(email);
        if (checkDuplicate.isPresent()){
            throw new IllegalArgumentException("중복 이메일 입니다.");
        }
        // 유저 등록
        userRepository.save(requestDto.toEntity());
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
        System.out.println("이메일은" + email);
        return findByEmail(email);
    }

    public void following(String loginUserEmail, FollowRequestDto followRequestDto) {
        Optional<User> loggedInUserOpt = userRepository.findByEmail(loginUserEmail);
        Optional<User> targetUserOpt = userRepository.findByEmail(followRequestDto.getTargetUserEmail());
        User loggedInUser = loggedInUserOpt.get();
        User targetUser;

        if (targetUserOpt.isEmpty()) {
            throw new IllegalArgumentException("상대 계정이 존재하지 않습니다.");
        }
        targetUser = targetUserOpt.get();

        // 이미 팔로잉 중이라면
        if (targetUser.getFollowers().contains(loggedInUser)) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }

        loggedInUser.getFollowing().add(targetUser);
        targetUser.getFollowers().add(loggedInUser);
        User user = userRepository.save(loggedInUser);
        User target = userRepository.save(targetUser);
//        addActivity(user, target);
    }
//    public void addActivity(User user, User target) {
//        Activity activity = new Activity();
//        activity.setFollowUser(target);
//        activity.setUser(user);
//
//        activityRepository.save(activity);
//    }


    public void unFollowing(String loginUserEmail, FollowRequestDto followRequestDto) {
        Optional<User> loggedInUserOpt = userRepository.findByEmail(loginUserEmail);
        Optional<User> targetUserOpt = userRepository.findByEmail(followRequestDto.getTargetUserEmail());
        User loggedInUser = loggedInUserOpt.get();
        User targetUser;

        if (targetUserOpt.isEmpty()) {
            throw new IllegalStateException("탈퇴한 회원입니다.");
        }
        targetUser = targetUserOpt.get();

        // 팔로우 관계 확인
        if (loggedInUser.getFollowing().contains(targetUser)) {
            // 언팔로우 수행
            loggedInUser.getFollowing().remove(targetUser);
            targetUser.getFollowers().remove(loggedInUser);
        }

        userRepository.save(loggedInUser);
        userRepository.save(targetUser);
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


    //새로 추가
//    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
//        String email = requestDto.getEmail();
//        String pwd = requestDto.getPwd();
//
//        // 사용자 확인
//        User user = userRepository.findByEmail(email).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
//        );
//
//        // 비밀번호 확인
//        if (!passwordEncoder.matches(pwd, user.getPwd())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        String token = jwtUtil.createToken(user.getEmail());
//        jwtUtil.addJwtToCookie(token, res);
//    }


}
