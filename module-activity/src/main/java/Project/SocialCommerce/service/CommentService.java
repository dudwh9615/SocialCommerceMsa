package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.Comment;
import Project.SocialCommerce.entity.Post;

public interface CommentService {

    void addComment(CommentingRequestDto requestDto, String jwt);

    void editComment(EditCommentRequestDto requestDto, String jwt);

    void delComment(EditCommentRequestDto requestDto, String jwt);

    void likesComment(LikeCommentDto commentDto, String jwt);

    void addFeed(Comment comment, Post post, UserResponseDto user);

    void addFeed(Comment comment, UserResponseDto user);
}
