package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.CommentingRequestDto;
import Project.SocialCommerce.model.Activity;
import Project.SocialCommerce.model.Comment;
import Project.SocialCommerce.model.Post;
import Project.SocialCommerce.model.User;
import Project.SocialCommerce.repository.ActivityRepository;
import Project.SocialCommerce.repository.CommentRepository;
import Project.SocialCommerce.repository.PostRepository;
import Project.SocialCommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ActivityRepository activityRepository;

    public void addComment(CommentingRequestDto requestDto, String email) {
        User user = userRepository.findByEmail(email).get();
        Optional<Post> postOpt = postRepository.findById(requestDto.getPostId());

        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }

        Post post = postOpt.get();

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        addActivity(user, savedComment);
    }
    public void addActivity(User user, Comment comment) {
        Activity activity = new Activity();
        activity.setComment(comment);
        activity.setUser(user);

        activityRepository.save(activity);
    }

//    public void editComment(EditCommentRequestDto requestDto, String email) {
//        Optional<Comment> Opt = commentRepository.findById(requestDto.getId());
//        if (Opt.isEmpty()) {
//            throw new IllegalArgumentException("댓글 정보가 없습니다.");
//        }
//        Comment comment = Opt.get();
//
//        if (!comment.getUser().getEmail().equals(email)) {
//            throw new IllegalArgumentException("수정 권한이 없습니다.");
//        }
//
//        comment.setContent(requestDto.getContent());
//
//        commentRepository.save(comment);
//    }
}
