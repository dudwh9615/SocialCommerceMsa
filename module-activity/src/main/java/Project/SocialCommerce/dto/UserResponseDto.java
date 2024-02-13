package Project.SocialCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
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
