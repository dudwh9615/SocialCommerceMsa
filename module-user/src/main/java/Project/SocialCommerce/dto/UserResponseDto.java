package Project.SocialCommerce.dto;

import Project.SocialCommerce.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String profile;
    private String greetings;

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfile(user.getProfile());
        dto.setGreetings(user.getGreetings());
        return dto;
    }
}
