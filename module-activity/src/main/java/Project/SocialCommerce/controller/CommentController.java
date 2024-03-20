package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.CommentingRequestDto;
import Project.SocialCommerce.dto.DelContentRequestDto;
import Project.SocialCommerce.dto.EditContentRequestDto;
import Project.SocialCommerce.dto.LikeContentDto;
import Project.SocialCommerce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> commenting(@RequestBody CommentingRequestDto requestDto, @CookieValue(name = "Authorization") String jwt) {
        commentService.addComment(requestDto, jwt);
        return ResponseEntity.ok("게시 완료");
    }

    @PutMapping
    public ResponseEntity<String> editComment(@RequestBody EditContentRequestDto editCommentRequestDto, @CookieValue(name = "Authorization") String jwt) {
        commentService.editComment(editCommentRequestDto, jwt);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestBody DelContentRequestDto delCommentRequestDto, @CookieValue(name = "Authorization") String jwt) {
        commentService.delComment(delCommentRequestDto, jwt);;
        return ResponseEntity.ok("댓글 삭제 완료");
    }

    @PostMapping("/interactions")
    public ResponseEntity<String> likesComment(@RequestBody LikeContentDto commentDto, @CookieValue(name = "Authorization") String jwt) {
        commentService.likesComment(commentDto, jwt);
        return ResponseEntity.ok("댓글 좋아요");
    }
}
