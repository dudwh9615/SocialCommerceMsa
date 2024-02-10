package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.service.InteractionService;
import Project.SocialCommerce.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final InteractionService interactionService;

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
    public ResponseEntity<String> editPost(@RequestBody EditPostRequestDto editPostRequestDto, Principal principal) {
        postService.editPost(editPostRequestDto, principal.getName());
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @PostMapping("/interactions")
    public ResponseEntity<String> likesPost(@RequestBody LikePostDto postDto, Principal principal) {
        interactionService.likesPost(postDto, principal.getName());
        return ResponseEntity.ok("게시글 좋아요");
    }

}