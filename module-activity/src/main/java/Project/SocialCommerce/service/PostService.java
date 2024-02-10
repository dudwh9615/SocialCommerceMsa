package Project.SocialCommerce.service;

import Project.SocialCommerce.controller.UserClient;
import Project.SocialCommerce.dto.EditPostRequestDto;
import Project.SocialCommerce.dto.PostRequestDto;
import Project.SocialCommerce.dto.PostResponseDto;
import Project.SocialCommerce.dto.UserResponseDto;
import Project.SocialCommerce.model.Comment;
import Project.SocialCommerce.model.Post;
import Project.SocialCommerce.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserClient userClient;
//    private final UserRepository userRepository;
//    private final ActivityRepository activityRepository;


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
//        addActivity(user, savedPost);
    }

//    public void addActivity(User user, Post post) {
//        Activity activity = new Activity();
//        activity.setPost(post);
//        activity.setUser(user);
//
//        activityRepository.save(activity);
//    }

    public PostResponseDto getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        return new PostResponseDto(post.get());
    }

    public void editPost(EditPostRequestDto editPostRequestDto, String email) {
        Optional<Post> editPostOpt = postRepository.findById(editPostRequestDto.getPostId());
        if (editPostOpt.isEmpty()){
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        Post editPost = editPostOpt.get();

//        if (!editPost.getUser().getEmail().equals(email)) {
//            throw new IllegalArgumentException("권한이 없는 사용자 입니다.");
//        }

        editPost.setContent(editPostRequestDto.getContent());

        postRepository.save(editPost);
    }

    public void delPost(EditPostRequestDto delPostRequestDto, String email) {
        Optional<Post> delPostOPt = postRepository.findById(delPostRequestDto.getPostId());
        if (delPostOPt.isEmpty()) {
            throw new IllegalArgumentException("없는 게시물 입니다.");
        }
        Post delPost = delPostOPt.get();

//        if (!delPost.getUser().getEmail().equals(email)) {
//            throw new IllegalArgumentException("권한이 없는 사용자 입니다.");
//        }

        postRepository.delete(delPost);
    }
}
