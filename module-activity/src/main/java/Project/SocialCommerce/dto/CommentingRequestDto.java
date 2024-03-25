package Project.SocialCommerce.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CommentingRequestDto {
    private Long postId;
    private String content;
}
