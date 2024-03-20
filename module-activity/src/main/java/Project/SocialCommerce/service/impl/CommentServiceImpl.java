package Project.SocialCommerce.service.impl;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.Comment;
import Project.SocialCommerce.entity.ContentTypeEnum;
import Project.SocialCommerce.entity.Post;
import Project.SocialCommerce.feign.NewsFeedClient;
import Project.SocialCommerce.feign.UserClient;
import Project.SocialCommerce.repository.CommentRepository;
import Project.SocialCommerce.repository.PostRepository;
import Project.SocialCommerce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final NewsFeedClient newsFeedClient;

    @Override
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

    // 댓글 생성 시 뉴스피드로 활동 내역 전송
    @Override
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

    @Override
    public void editComment(EditContentRequestDto requestDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(requestDto.getContentId());
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

    @Override
    public void delComment(DelContentRequestDto delCommentRequestDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(delCommentRequestDto.getContentId());
        if (Opt.isEmpty()) {
            throw new IllegalArgumentException("댓글 정보가 없습니다.");
        }
        Comment comment = Opt.get();

        if (!comment.getUserEmail().equals(userClient.findByJwt(jwt).getEmail())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }

    @Override
    public void likesComment(LikeContentDto commentDto, String jwt) {
        Optional<Comment> Opt = commentRepository.findById(commentDto.getContentId());
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

    // 댓글 좋아요 시 뉴스피드로 활동 내역 전송
    @Override
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
