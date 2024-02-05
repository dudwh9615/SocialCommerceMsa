package Project.SocialCommerce.repository;

import Project.SocialCommerce.model.Activity;
import Project.SocialCommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Page<Activity> findByUserInOrderByCreatedAtDesc(List<User> following, Pageable pageable);
}
