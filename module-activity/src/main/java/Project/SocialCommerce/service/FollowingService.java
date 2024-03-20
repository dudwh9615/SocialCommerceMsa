package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.FollowRequestDto;
import Project.SocialCommerce.dto.UserResponseDto;
import java.util.List;

public interface FollowingService {

    void following(String jwt, FollowRequestDto followRequestDto);

    void addFeed(UserResponseDto user, UserResponseDto target);

    void unFollowing(String jwt, FollowRequestDto followRequestDto);

    void createFollowInfo(Long id);

    List<Long> getLoginFollowingList(String jwt);
}
