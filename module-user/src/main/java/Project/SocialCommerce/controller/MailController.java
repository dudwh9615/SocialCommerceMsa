package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.email.EmailCheckDto;
import Project.SocialCommerce.dto.email.EmailRequestDto;
import Project.SocialCommerce.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send-auth")
    public String sendAuthMail(@RequestBody @Valid EmailRequestDto requestDto) {
        return mailService.joinEmail(requestDto.getEmail());
    }

    /**
     * 회원가입 후 인증하는 방식?
     * 이렇게 되면 mailService joinemail에서 중복 이메일에 대해서 검사하는게 아니라
     * User의 값에 인증 유무 컬럼 생성 후  인증 유무에 대해 검사해야
     * 말이 됧 듯?
     *
     * 일단은 redis에 값이 잘 저장되는지 확인 완료
     */
    @PostMapping("/check-auth")
    public ResponseEntity<String> authCheck(@RequestBody @Valid EmailCheckDto checkDto) {
        Boolean ischked = mailService.checkAuth(checkDto.getEmail(), checkDto.getAuthNum());
        if (ischked) {
            /**
             * 이메일 인증 성공으로 User정보 업데이트 후
             * redis의 이메일, 인증번호 데이터 삭제해야 할 듯
             */
            return ResponseEntity.ok("인증 완료");
        }
        return ResponseEntity.badRequest().body("인증 오류");

    }
}
