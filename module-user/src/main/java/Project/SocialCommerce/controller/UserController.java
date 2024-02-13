package Project.SocialCommerce.controller;

import Project.SocialCommerce.dto.*;
import Project.SocialCommerce.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> findByLoginEmail(Principal principal){
        return ResponseEntity.ok(userService.findByEmail(principal.getName()));
    }

    @GetMapping("/jwt/{jwt}")
    public UserResponseDto findByJwt(@PathVariable String jwt){
        return userService.findByJwt(jwt);
    }

    @GetMapping("/email/{email}")
    public UserResponseDto findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto requestDto) {
        //UserService 호출해서 회원가입
        userService.register(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PutMapping
    public ResponseEntity<String> modifyUserInfo(@RequestBody @Valid ModifyRequestDto modifyRequestDto, Principal principal, HttpServletResponse httpServletResponse) {
        userService.modifyUserInfo(modifyRequestDto, principal.getName());
        if (modifyRequestDto.getPwd() != null){
            try {
                httpServletResponse.sendRedirect("/logout");
            } catch (Exception e) {
                e.toString();
            }

        }
        return ResponseEntity.ok("회원정보 수정 완료");
    }


//    @PostMapping("/login")
//    public void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) {
//        userService.login(loginRequestDto, );
//        return ResponseEntity.
//    }

}
