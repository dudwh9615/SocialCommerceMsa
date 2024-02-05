package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.ActivityDto;
import Project.SocialCommerce.model.Activity;
import Project.SocialCommerce.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping
    public Page<ActivityDto> myFeed(Pageable pageable, Principal principal) {
        return activityService.myFeed(pageable, principal.getName());
    }
}
