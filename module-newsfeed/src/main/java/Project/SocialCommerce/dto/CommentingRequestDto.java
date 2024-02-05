package Project.SocialCommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentingRequestDto {
    private Long postId;
    private String content;
}
