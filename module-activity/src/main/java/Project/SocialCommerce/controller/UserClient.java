package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@FeignClient("service-user")
public interface UserClient{
// 유저 서비스에 있는 클라이언트 메서드와 동일하게 구성해야 함
// Principal을 사용할 수 있다면 좋은데 어떻게 해야할지 고민 중
    @GetMapping("/users/{jwt}")
    UserResponseDto findByJwt(@PathVariable String jwt);
}
