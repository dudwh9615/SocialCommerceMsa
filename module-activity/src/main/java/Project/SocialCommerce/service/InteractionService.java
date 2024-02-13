package Project.SocialCommerce.service;

import Project.SocialCommerce.controller.UserClient;
import Project.SocialCommerce.dto.LikeCommentDto;
import Project.SocialCommerce.dto.LikePostDto;
import Project.SocialCommerce.dto.UserResponseDto;
import Project.SocialCommerce.model.*;
import Project.SocialCommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserClient userClient;
//    private final ActivityRepository activityRepository;

    public void likesComment(LikeCommentDto dto, String jwt) {
        Interaction likes = new Interaction();
        Optional<Comment> commentOpt = commentRepository.findById(dto.getCommentId());
        if (commentOpt.isEmpty()) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }
        Comment comment = commentOpt.get();
        Optional<Post> postOpt = postRepository.findById(comment.getPost().getId());
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException(("댓글이 달린 게시물이 존재하지 않습니다."));
        }
        Post post = postOpt.get();
        UserResponseDto userResponseDto = userClient.findByJwt(jwt);
        likes.setComment(comment);
        likes.setUserEmail(userResponseDto.getEmail());

        Interaction saved = interactionRepository.save(likes);
//        addActivity(user, saved);
    }

    public void likesPost(LikePostDto dto, String jwt) {
        Interaction likes = new Interaction();
        Optional<Post> postOpt = postRepository.findById(dto.getPostId());
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException(("게시물이 존재하지 않습니다."));
        }
        Post post = postOpt.get();
        UserResponseDto userResponseDto = userClient.findByJwt(jwt);
        likes.setPost(post);
        likes.setUserEmail(userResponseDto.getEmail());
        if (post.getInteractionUser().contains(userResponseDto.getId())) {
            throw new IllegalArgumentException("좋아요 한 계정입니다.");
        }
        post.getInteractionUser().add(userResponseDto.getId());
        Interaction saved = interactionRepository.save(likes);
//        addActivity(user, saved);
    }

//    public void addActivity(User user, Interaction interaction) {
//        Activity activity = new Activity();
//        activity.setInteraction(interaction);
//        activity.setUser(user);
//
//        activityRepository.save(activity);
//    }
}
