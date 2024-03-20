package Project.SocialCommerce.service.impl;

import Project.SocialCommerce.dto.FeedDto;
import Project.SocialCommerce.dto.FollowRequestDto;
import Project.SocialCommerce.dto.UserResponseDto;
import Project.SocialCommerce.entity.ContentTypeEnum;
import Project.SocialCommerce.entity.Following;
import Project.SocialCommerce.feign.NewsFeedClient;
import Project.SocialCommerce.feign.UserClient;
import Project.SocialCommerce.repository.FollowingRepository;
import Project.SocialCommerce.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowingServiceImpl implements FollowingService {

    private final FollowingRepository followingRepository;
    private final UserClient userClient;
    private final NewsFeedClient newsFeedClient;

    @Override
    public void following(String jwt, FollowRequestDto followRequestDto) {
        UserResponseDto loginUser = userClient.findByJwt(jwt);
        UserResponseDto targetUser = userClient.findByEmail(followRequestDto.getTargetUserEmail());

        Following loginUserRel = followingRepository.findByMyId(loginUser.getId()).get();
        Following targetUserRel = followingRepository.findByMyId(targetUser.getId()).get();
        if (loginUserRel.getFollowing().contains(targetUser.getId())) {
            throw new IllegalArgumentException("이미 팔로잉 중 입니다..");
        }
        loginUserRel.getFollowing().add(targetUser.getId());
        targetUserRel.getFollower().add(loginUser.getId());

        followingRepository.save(loginUserRel);
        followingRepository.save(targetUserRel);
        addFeed(loginUser, targetUser);
    }

    @Override
    public void addFeed(UserResponseDto user, UserResponseDto target) {
        FeedDto newFeed = new FeedDto();

        newFeed.setUserId(user.getId());
        newFeed.setUserName(user.getName());
        newFeed.setTargetUserId(target.getId());
        newFeed.setTargetUserName(target.getName());
        newFeed.setDoing(ContentTypeEnum.FOLLOW);
        newFeed.setCreatedAt(LocalDateTime.now());

        newsFeedClient.addFeed(newFeed);
    }

    @Override
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

    @Override
    public void createFollowInfo(Long id) {
        Following newUser = new Following();
        newUser.setMyId(id);
        followingRepository.save(newUser);
    }

    @Override
    public List<Long> getLoginFollowingList(String jwt) {
        Long userId = userClient.findByJwt(jwt).getId();
        return followingRepository.findByMyId(userId).orElseThrow().getFollowing();
    }
}
