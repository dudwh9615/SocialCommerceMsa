package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.FeedDto;
import Project.SocialCommerce.model.NewsFeed;
import Project.SocialCommerce.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @PostMapping
    public void addFeed(@RequestBody FeedDto feedDto) {
        newsFeedService.addFeed(feedDto);
        System.out.println("활동 저장 완료");
    }

    @GetMapping
    public Page<NewsFeed> myFeed(Pageable pageable, @CookieValue(name = "Authorization") String jwt) {
        return newsFeedService.myFeed(pageable, jwt);
    }
}
