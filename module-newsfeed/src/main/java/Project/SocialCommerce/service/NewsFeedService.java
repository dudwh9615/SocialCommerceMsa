package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.FeedDto;
import Project.SocialCommerce.entity.NewsFeed;
import Project.SocialCommerce.repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final ActivityClient activityClient;

    public Page<NewsFeed> myFeed(Pageable pageable, String jwt) {
        List<Long> following = activityClient.getUserFollowingList(jwt);

        return newsFeedRepository.findByUserIdInOrderByCreatedAtDesc(following, pageable);
    }

    public void addFeed(FeedDto feedDto) {
        System.out.println(feedDto.getUserName());
        newsFeedRepository.save(toEntity(feedDto));
    }

    public NewsFeed toEntity(FeedDto feedDto) {
        NewsFeed newsFeed = new NewsFeed();

        newsFeed.setUserId(feedDto.getUserId());
        newsFeed.setUserName(feedDto.getUserName());
        newsFeed.setTargetUserId(feedDto.getTargetUserId());
        newsFeed.setTargetUserName(feedDto.getTargetUserName());
        newsFeed.setPostId(feedDto.getPostId());
        newsFeed.setCommentId(feedDto.getCommentId());
        newsFeed.setDoing(feedDto.getDoing());
        newsFeed.setCreatedAt(feedDto.getCreatedAt());

        return newsFeed;
    }
}
