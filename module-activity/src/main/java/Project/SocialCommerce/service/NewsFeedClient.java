package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.FeedDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-newsfeed")
public interface NewsFeedClient {
    @PostMapping("/feeds")
    void addFeed(FeedDto feedDto);
}
