package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.ActivityDto;
import Project.SocialCommerce.model.Activity;
import Project.SocialCommerce.model.User;
import Project.SocialCommerce.repository.ActivityRepository;
import Project.SocialCommerce.repository.CommentRepository;
import Project.SocialCommerce.repository.PostRepository;
import Project.SocialCommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public Page<ActivityDto> myFeed(Pageable pageable, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        List<User> following = userOpt.get().getFollowing();

        Page<Activity> res = activityRepository.findByUserInOrderByCreatedAtDesc(following, pageable);

        return res.map(ActivityDto::toDto);
    }
}
