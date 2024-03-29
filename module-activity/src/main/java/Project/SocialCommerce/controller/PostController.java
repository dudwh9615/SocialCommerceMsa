package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> posting(@RequestBody PostRequestDto postRequestDto, @CookieValue(name = "Authorization") String jwt) {
        postService.addPost(postRequestDto, jwt);
        return ResponseEntity.ok("게시 완료");
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PutMapping
    public ResponseEntity<String> editPost(@RequestBody EditContentRequestDto editPostRequestDto, @CookieValue(name = "Authorization") String jwt) {
        postService.editPost(editPostRequestDto, jwt);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(@RequestBody DelContentRequestDto delPostRequestDto, @CookieValue(name = "Authorization") String jwt) {
        postService.delPost(delPostRequestDto, jwt);
        return ResponseEntity.ok("게시글 삭제 완료");
    }

    @PostMapping("/interactions")
    public ResponseEntity<String> likesPost(@RequestBody LikeContentDto postDto, @CookieValue(name = "Authorization") String jwt) {
        postService.likesPost(postDto, jwt);
        return ResponseEntity.ok("게시글 좋아요");
    }

}