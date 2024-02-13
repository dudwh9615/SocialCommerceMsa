package Project.SocialCommerce.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-activity")
public interface ActivityClient {
    @PostMapping("/contents/{id}")
    void createFollowInfo(@PathVariable("id") Long id);
}
