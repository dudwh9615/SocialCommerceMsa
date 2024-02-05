package Project.SocialCommerce.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EditPostRequestDto {
    private Long postId;

    @Lob()
    private String content;
}
