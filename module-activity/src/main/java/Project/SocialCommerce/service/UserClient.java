package Project.SocialCommerce.service;

import Project.SocialCommerce.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserClient{
// 유저 서비스에 있는 클라이언트 메서드와 동일하게 구성해야 함
// Principal을 사용할 수 있다면 좋은데 어떻게 해야할지 고민 중
    @GetMapping("/users/jwt/{jwt}")
    UserResponseDto findByJwt(@PathVariable String jwt);
    @GetMapping("/users/email/{email}")
    UserResponseDto findByEmail(@PathVariable String email);
}
