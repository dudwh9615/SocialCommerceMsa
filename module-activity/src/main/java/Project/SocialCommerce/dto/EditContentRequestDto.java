package Project.SocialCommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditContentRequestDto {
    private Long contentId;
    private String content;
}
