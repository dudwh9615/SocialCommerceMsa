package Project.SocialCommerce.service;

import Project.SocialCommerce.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisUtil;
    private final UserRepository userRepository;

    private int authNumber;

    // 인증번호 생성
    public void makeAuthNumber() {
        int len = 6;
        Random random = new Random();
        String authnum = "";
        for (int i = 0; i < len; i++) {
            authnum += Integer.toString(random.nextInt(10));
        }
        authNumber = Integer.parseInt(authnum);
    }

    //전송할 이메일 정보 작성
    public String joinEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "이미 저장된 회원 이메일 입니다.";
        }
        makeAuthNumber();
        String setFrom = "jeff.hyjo@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "나의 APP을 방문해주셔서 감사합니다." + 	//html 형식으로 작성 !
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 3분이내에 입력해주세요"; //이메일 내용 삽입
        mailSend(setFrom, email, title, content);
        return Integer.toString(authNumber);
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            javaMailSender.send(message);
            redisUtil.setDataExpire(toMail, Integer.toString(authNumber), 180);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }


    }
    // 메일 인증번호 전달 시 처리
    public Boolean checkAuth(String email, String authNum) {
        return redisUtil.getAuth(email).equals(authNum);
    }
}
