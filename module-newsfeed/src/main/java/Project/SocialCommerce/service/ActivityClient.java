package Project.SocialCommerce.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("service-activity")
public interface ActivityClient {
    @GetMapping("/contents/following/{jwt}")
    List<Long> getUserFollowingList(@PathVariable String jwt);


}
