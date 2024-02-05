package Project.SocialCommerce.dto;

import Project.SocialCommerce.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDto {
    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String pwd;

    @NotBlank
    private String name;

    @NotBlank
    private String profile;

    @NotBlank
    private String greetings;

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setPwd(pwd);
        user.setName(name);
        user.setProfile(profile);
        user.setGreetings(greetings);

        return user;
    }

}
