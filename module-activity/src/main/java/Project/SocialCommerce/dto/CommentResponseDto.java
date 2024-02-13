package Project.SocialCommerce.dto;

import Project.SocialCommerce.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String userEmail;
    private String content;

    public CommentResponseDto (Comment comment) {
        setCommentId(comment.getId());
        setUserEmail(comment.getUserEmail());
        setContent(comment.getContent());
    }
}
