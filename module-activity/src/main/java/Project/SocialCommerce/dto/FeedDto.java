package Project.SocialCommerce.dto;


import Project.SocialCommerce.model.ContentTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class FeedDto {

    private Long userId;// A유저가
    private String userName;

    private Long targetUserId;// B유저의
    private String targetUserName;

    // [포스트, 댓글] 컨텐츠에
    private Long postId;
    private Long commentId;

    /**
     * [포스트, 댓글, 좋아요]를 했다
     * => 포스트 == A유저가 포스트를 게시한 것
     */
    private ContentTypeEnum doing;

    private LocalDateTime createdAt;
}
