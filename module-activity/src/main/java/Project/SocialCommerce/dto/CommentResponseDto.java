package Project.SocialCommerce.dto;

import Project.SocialCommerce.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String userEmail;
    private String content;
    private Long interactionUserCount;
    private LocalDateTime CreatedAt;

    public CommentResponseDto (Comment comment) {
        setCommentId(comment.getId());
        setUserEmail(comment.getUserEmail());
        setContent(comment.getContent());
        setInteractionUserCount(comment.getInteractionUser().stream().count());
        setCreatedAt(comment.getCreatedAt());
    }
}
