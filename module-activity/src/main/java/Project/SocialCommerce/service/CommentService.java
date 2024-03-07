package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;

import Project.SocialCommerce.entity.Comment;
import Project.SocialCommerce.entity.ContentTypeEnum;
import Project.SocialCommerce.entity.Post;

import Project.SocialCommerce.repository.CommentRepository;
import Project.SocialCommerce.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final NewsFeedClient newsFeedClient;

    public void addComment(CommentingRequestDto requestDto, String jwt) {
        UserResponseDto userResponseDto = userClient.findByJwt(jwt);
        Optional<Post> postOpt = postRepository.findById(requestDto.getPostId());

        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }

        Post post = postOpt.get();

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setUserEmail(userResponseDto.getEmail());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(comment);
        addFeed(savedComment, post, userResponseDto);
    }
    public void addFeed(Comment comment, Post post, UserResponseDto user) {
        FeedDto newFeed = new FeedDto();
        UserResponseDto targetUser = userClient.findByEmail(post.getEmail());

        newFeed.setUserId(user.getId());
        newFeed.setUserName(user.getName());
        newFeed.setTargetUserId(targetUser.getId());
        newFeed.setTargetUserName(targetUser.getName());
        newFeed.setCommentId(comment.getId());
        newFeed.setDoing(ContentTypeEnum.COMMENT);
        newFeed.setCreatedAt(comment.getCreatedAt());

        System.out.print("여기여기" + newFeed.getUserName());
        newsFeedClient.addFeed(newFeed);
    }

    public void editComment(EditCommentRequestDto requestDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(requestDto.getCommentId());
        if (Opt.isEmpty()) {
            throw new IllegalArgumentException("댓글 정보가 없습니다.");
        }
        Comment comment = Opt.get();

        if (!comment.getUserEmail().equals(userClient.findByJwt(jwt).getEmail())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        comment.setContent(requestDto.getContent());

        commentRepository.save(comment);
    }

    public void delComment(EditCommentRequestDto requestDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(requestDto.getCommentId());
        if (Opt.isEmpty()) {
            throw new IllegalArgumentException("댓글 정보가 없습니다.");
        }
        Comment comment = Opt.get();

        if (!comment.getUserEmail().equals(userClient.findByJwt(jwt).getEmail())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    public void likesComment(LikeCommentDto commentDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(commentDto.getCommentId());
        UserResponseDto user = userClient.findByJwt(jwt);
        if (Opt.isEmpty()) {
            throw new IllegalArgumentException("댓글 정보가 없습니다.");
        }
        Comment comment = Opt.get();

        if (comment.getInteractionUser().contains(user.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
        }

        comment.getInteractionUser().add(user.getId());

        commentRepository.save(comment);
        addFeed(comment, user);
    }
    public void addFeed(Comment comment, UserResponseDto user) {
        FeedDto newFeed = new FeedDto();
        UserResponseDto targetUser = userClient.findByEmail(comment.getUserEmail());

        newFeed.setUserId(user.getId());
        newFeed.setUserName(user.getName());
        newFeed.setTargetUserId(targetUser.getId());
        newFeed.setTargetUserName(targetUser.getName());
        newFeed.setCommentId(comment.getId());
        newFeed.setDoing(ContentTypeEnum.LIKES);
        newFeed.setCreatedAt(LocalDateTime.now());

        newsFeedClient.addFeed(newFeed);
    }
}
