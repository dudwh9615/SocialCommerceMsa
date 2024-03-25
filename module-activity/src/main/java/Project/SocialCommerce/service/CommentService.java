package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.Comment;
import Project.SocialCommerce.entity.Post;

public interface CommentService {

    void addComment(CommentingRequestDto requestDto, String jwt);

    void editComment(EditContentRequestDto editCommentRequestDto, String jwt);

    void delComment(DelContentRequestDto delCommentRequestDto, String jwt);

    void likesComment(LikeContentDto commentDto, String jwt);

    void addFeed(Comment comment, Post post, UserResponseDto user);

    void addFeed(Comment comment, UserResponseDto user);
}
