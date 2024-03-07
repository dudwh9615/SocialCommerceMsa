package Project.SocialCommerce.repository;

import Project.SocialCommerce.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
    Page<NewsFeed> findByUserIdInOrderByCreatedAtDesc(List<Long> following, Pageable pageable);
}
