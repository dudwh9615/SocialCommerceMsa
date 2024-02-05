package Project.SocialCommerce.dto;


import Project.SocialCommerce.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ActivityDto {
    // A유저가
    private String doUserEmail;
    // [포스트, 댓글, 좋아요] 를 등록했다 **포스트인 경우는 A유저가 포스트를 게시한 것
    private ContentTypeEnum doing;
    // B유저의
    private String targetUserEmail;
    // 해당 Id를 갖는
    private Long targetContentId;
    // [포스트, 댓글] 컨텐츠에
    private ContentTypeEnum targetContentType;

    private String createdAt;

    private String postContent;

    public static ActivityDto toDto(Activity activity) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setDoUserEmail(activity.getUser().getEmail());

        // 게시글 작성을 했을 때
        if (activity.getPost() != null) {
            activityDto.setDoing(ContentTypeEnum.POST);
            activityDto.setTargetContentId(activity.getPost().getId());
            activityDto.setPostContent(activity.getPost().getContent());
            activityDto.setCreatedAt(activity.getPost().getCreatedAt().toString());

            // 댓글 작성을 했을 때
        } else if (activity.getComment() != null) {
            activityDto.setDoing(ContentTypeEnum.COMMENT);
            activityDto.setTargetUserEmail(activity.getComment().getPost().getUser().getEmail());
            activityDto.setTargetContentId(activity.getComment().getPost().getId());
            activityDto.setTargetContentType(ContentTypeEnum.POST);
            activityDto.setCreatedAt(activity.getComment().getCreatedAt().toString());

            // 좋아요를 눌렀을 때
        } else if (activity.getInteraction() != null) {
            activityDto.setDoing(ContentTypeEnum.LIKES);
            if (activity.getInteraction().getPost() != null) {
                activityDto.setTargetUserEmail(activity.getInteraction().getPost().getUser().getEmail());
                activityDto.setTargetContentId(activity.getInteraction().getPost().getId());
                activityDto.setTargetContentType(ContentTypeEnum.POST);
                activityDto.setCreatedAt(activity.getInteraction().getCreatedAt().toString());
            } else {
                activityDto.setTargetUserEmail(activity.getInteraction().getComment().getUser().getEmail());
                activityDto.setTargetContentId(activity.getInteraction().getComment().getId());
                activityDto.setTargetContentType(ContentTypeEnum.COMMENT);
                activityDto.setCreatedAt(activity.getInteraction().getCreatedAt().toString());
            }

            // 팔로우를 했을 때
        } else {
            activityDto.setDoing(ContentTypeEnum.FOLLOW);
            activityDto.setDoUserEmail(activity.getUser().getEmail());
            activityDto.setTargetUserEmail(activity.getFollowUser().getEmail());
        }
        return activityDto;
    }
}
