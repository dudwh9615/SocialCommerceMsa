package Project.SocialCommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Table(name = "activities")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class NewsFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;// A유저가
    private String userName;

    private Long targetUserId;// B유저의
    private String targetUserName;

    // 컨텐츠에
    private Long postId;
    private Long commentId;

    // ??을 했다
    private ContentTypeEnum doing;
    /**
     * 게시글을 작성  postId [do post] --> 게시글 정보
     * 게시글에 댓글 남김 targetUser postId commentId [do comment] --> 댓글 정보
     * 게시글에 좋아요 남김 targetUser postId [do likes]
     * 댓글에 좋아요 남김 targetUser commentId [do likes]
     * ?를 팔로우 함 targetUser [do follow]
     */

    private LocalDateTime createdAt;
}
