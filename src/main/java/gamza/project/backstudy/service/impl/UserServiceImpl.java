package gamza.project.backstudy.service.impl;

import gamza.project.backstudy.Error.ErrorCode;
import gamza.project.backstudy.Error.requestError.NotFoundException;
import gamza.project.backstudy.Error.requestError.UnAuthorizedException;
import gamza.project.backstudy.dto.user.UserLoginRequestDto;
import gamza.project.backstudy.dto.user.UserSignUpRequestDto;
import gamza.project.backstudy.entity.Enum.UserRole;
import gamza.project.backstudy.entity.UserEntity;
import gamza.project.backstudy.repository.UserRepository;
import gamza.project.backstudy.service.inter.UserService;
import gamza.project.backstudy.service.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signUp(UserSignUpRequestDto dto, HttpServletResponse response) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UnAuthorizedException("ErrorCode : 00x1", ErrorCode.NOT_ALLOW_ACCESS_EXCEPTION);
        }

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        UserEntity user = dto.toEntity();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void login(UserLoginRequestDto dto, HttpServletResponse response) {

        if(!userRepository.existsByEmail(dto.getEmail())) {
            throw new UnAuthorizedException("ErrorCode : 00x2", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }

        UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("ErrorCode : 00x3", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }

        setTokenInHeader(dto.getEmail(), response);
    }

    @Override
//    @Transactional
    public void setTokenInHeader(String email, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow();

        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }
}
