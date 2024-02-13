package Project.SocialCommerce.dto;


import Project.SocialCommerce.model.Comment;
import Project.SocialCommerce.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponseDto {

    private Long id;
    private String createUserEmail;
    private String content;
    private String createdAt;
    private Long likesCount;
    private List<CommentResponseDto> comments;


    public PostResponseDto(Post post) {
        setId(post.getId());
        setCreateUserEmail(post.getEmail());
        setContent(post.getContent());
        setCreatedAt(post.getCreatedAt().toString());
        setLikesCount(post.getInteractionUser().stream().count());
        setComments(post.getComments().stream().map(CommentResponseDto::new).toList());
    }
}
