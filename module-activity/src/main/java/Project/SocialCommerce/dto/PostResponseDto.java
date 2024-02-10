package Project.SocialCommerce.dto;


import Project.SocialCommerce.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponseDto {

    private Long id;
    private String createUserEmail;
    private String content;
    private String createdAt;

    public PostResponseDto(Post post) {
        setId(post.getId());
        setCreateUserEmail(post.getEmail());
        setContent(post.getContent());
        setCreatedAt(post.getCreatedAt().toString());
    }
}
