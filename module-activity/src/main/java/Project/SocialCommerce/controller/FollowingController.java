package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.FollowRequestDto;
import Project.SocialCommerce.service.FollowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class FollowingController {

    private final FollowingService followingService;

    // 회원 가입 시 사용자의 팔로우 관계 엔티티 생성
    @PostMapping("/{id}")
    void createFollowInfo(@PathVariable("id") Long id) {
        followingService.createFollowInfo(id);
    }

    @PostMapping("/following")
    public ResponseEntity<String> followUser(@RequestBody FollowRequestDto followRequestDto, @CookieValue(name = "Authorization") String jwt) {
        followingService.following(jwt, followRequestDto);
        return ResponseEntity.ok("팔로우 성공");
    }


    @PostMapping("/unfollowing")
    public ResponseEntity<String> unFollowUser(@RequestBody FollowRequestDto followRequestDto, @CookieValue(name = "Authorization") String jwt) {
        followingService.unFollowing(jwt, followRequestDto);
        return ResponseEntity.ok("팔로우 취소");
    }
}
