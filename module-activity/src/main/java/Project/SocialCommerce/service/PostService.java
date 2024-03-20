package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.entity.Post;


public interface PostService {

    void addPost(PostRequestDto postRequestDto, String jwt);

    PostResponseDto getPost(Long postId);

    void editPost(EditContentRequestDto editPostRequestDto, String jwt);

    void delPost(DelContentRequestDto delPostRequestDto, String jwt);

    void likesPost(LikeContentDto postDto, String jwt);

    void addFeed(Post post, UserResponseDto user);

    void addFeed(Post post, UserResponseDto user, String targetEmail);
}
