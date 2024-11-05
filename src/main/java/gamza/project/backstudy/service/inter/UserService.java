package gamza.project.backstudy.service.inter;

import gamza.project.backstudy.dto.user.UserLoginRequestDto;
import gamza.project.backstudy.dto.user.UserSignUpRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    void signUp(UserSignUpRequestDto dto, HttpServletResponse response);

    void login(UserLoginRequestDto dto, HttpServletResponse response);

    void setTokenInHeader(String email, HttpServletResponse response);

}
