package Project.SocialCommerce.repository;

import Project.SocialCommerce.model.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following, Long> {
    Optional<Following> findByMyId(Long myId);
}
