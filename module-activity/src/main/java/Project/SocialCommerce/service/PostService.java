package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.ContentTypeEnum;
import Project.SocialCommerce.entity.Post;
import Project.SocialCommerce.repository.CommentRepository;
import Project.SocialCommerce.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserClient userClient;
    private final CommentRepository commentRepository;
    private final NewsFeedClient newsFeedClient;


    public void addPost(PostRequestDto postRequestDto, String jwt) {
        System.out.println("====================================jwt입니다." + jwt);
        UserResponseDto userResponseDto = userClient.findByJwt(jwt);
        if (userResponseDto == null){
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        Post newPost = new Post();

        newPost.setContent(postRequestDto.getContent());
        newPost.setEmail(userResponseDto.getEmail());

        Post savedPost = postRepository.save(newPost);
        addFeed(savedPost, userResponseDto);
    }
    public void addFeed(Post post, UserResponseDto user) {
        FeedDto newFeed = new FeedDto();

        newFeed.setUserId(user.getId());
        newFeed.setUserName(user.getName());
        newFeed.setPostId(post.getId());
        newFeed.setDoing(ContentTypeEnum.POST);
        newFeed.setCreatedAt(post.getCreatedAt());

        newsFeedClient.addFeed(newFeed);
    }

    public PostResponseDto getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        return new PostResponseDto(post.get());
    }

    public void editPost(EditPostRequestDto editPostRequestDto, String jwt) {
        Optional<Post> editPostOpt = postRepository.findById(editPostRequestDto.getPostId());
        if (editPostOpt.isEmpty()){
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        Post editPost = editPostOpt.get();

        if (!editPost.getEmail().equals(userClient.findByJwt(jwt).getEmail())) {
            throw new IllegalArgumentException("권한이 없는 사용자 입니다.");
        }

        editPost.setContent(editPostRequestDto.getContent());

        postRepository.save(editPost);
    }

    public void delPost(EditPostRequestDto delPostRequestDto, String jwt) {
        Optional<Post> delPostOPt = postRepository.findById(delPostRequestDto.getPostId());
        if (delPostOPt.isEmpty()) {
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        Post delPost = delPostOPt.get();

        if (!delPost.getEmail().equals(userClient.findByJwt(jwt).getEmail())) {
            throw new IllegalArgumentException("권한이 없는 사용자 입니다.");
        }

        commentRepository.deleteAll(delPost.getComments());

        postRepository.delete(delPost);
    }

    public void likesPost(LikePostDto postDto, String jwt) {
        Optional<Post> targetPostOpt = postRepository.findById(postDto.getPostId());
        UserResponseDto user = userClient.findByJwt(jwt);
        if (targetPostOpt.isEmpty()) {
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        Post targetPost = targetPostOpt.get();

        if (targetPost.getInteractionUser().contains(user.getId())) {
            throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
        }

        targetPost.getInteractionUser().add(user.getId());

        postRepository.save(targetPost);
        addFeed(targetPost, user, targetPost.getEmail());
    }
    public void addFeed(Post post, UserResponseDto user, String targetEmail) {
        FeedDto newFeed = new FeedDto();
        UserResponseDto target = userClient.findByEmail(targetEmail);

        newFeed.setUserId(user.getId());
        newFeed.setUserName(user.getName());
        newFeed.setTargetUserId(target.getId());
        newFeed.setTargetUserName(target.getName());
        newFeed.setPostId(post.getId());
        newFeed.setDoing(ContentTypeEnum.LIKES);
        newFeed.setCreatedAt(LocalDateTime.now());

        newsFeedClient.addFeed(newFeed);
    }
}
