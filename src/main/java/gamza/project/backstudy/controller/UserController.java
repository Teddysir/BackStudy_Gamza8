package gamza.project.backstudy.controller;

import gamza.project.backstudy.dto.user.UserLoginRequestDto;
import gamza.project.backstudy.dto.user.UserSignUpRequestDto;
import gamza.project.backstudy.service.inter.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequestDto dto, HttpServletResponse response) {
        userService.signUp(dto, response);
        return ResponseEntity.ok().body("Success Sing Up!\nIf you want to see the PK value, please ask SH :)");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto dto, HttpServletResponse response) {
        userService.login(dto, response);
        return ResponseEntity.ok().body("Success Login!");
    }
}
