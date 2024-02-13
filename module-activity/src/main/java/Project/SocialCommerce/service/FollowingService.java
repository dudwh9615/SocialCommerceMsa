package Project.SocialCommerce.service;

import Project.SocialCommerce.controller.UserClient;
import Project.SocialCommerce.dto.FollowRequestDto;
import Project.SocialCommerce.dto.UserResponseDto;
import Project.SocialCommerce.model.Following;
import Project.SocialCommerce.repository.FollowingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserClient userClient;

    public void following(String jwt, FollowRequestDto followRequestDto) {
        Long loginUserId = userClient.findByJwt(jwt).getId();
        Long targetUserId = userClient.findByEmail(followRequestDto.getTargetUserEmail()).getId();

        Following loginUser = followingRepository.findByMyId(loginUserId).get();
        Following targetUser = followingRepository.findByMyId(targetUserId).get();
        if (loginUser.getFollowing().contains(targetUserId)) {
            throw new IllegalArgumentException("이미 팔로잉 중 입니다..");
        }
        loginUser.getFollowing().add(targetUserId);
        targetUser.getFollower().add(loginUserId);

        followingRepository.save(loginUser);
        followingRepository.save(targetUser);
    }

    public void unFollowing(String jwt, FollowRequestDto followRequestDto) {
        Long loginUserId = userClient.findByJwt(jwt).getId();
        Long targetUserId = userClient.findByEmail(followRequestDto.getTargetUserEmail()).getId();

        Following loginUser = followingRepository.findByMyId(loginUserId).get();
        Following targetUser = followingRepository.findByMyId(targetUserId).get();
        if (!loginUser.getFollowing().contains(targetUserId)) {
            throw new IllegalArgumentException("팔로잉 정보가 없습니다.");
        }
        loginUser.getFollowing().remove(targetUserId);
        targetUser.getFollower().remove(loginUserId);

        followingRepository.save(loginUser);
        followingRepository.save(targetUser);
    }

    public void createFollowInfo(Long id) {
        Following newUser = new Following();
        newUser.setMyId(id);
        followingRepository.save(newUser);
    }
}
