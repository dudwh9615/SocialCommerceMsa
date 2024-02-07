package Project.SocialCommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private String email;
    private String name;
    private String profile;
    private String greetings;

//    public static UserResponseDto toDto(User user) {
//        UserResponseDto dto = new UserResponseDto();
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setProfile(user.getProfile());
//        dto.setGreetings(user.getGreetings());
//        return dto;
//    }
}
