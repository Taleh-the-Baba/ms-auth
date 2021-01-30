package com.guavapay.ms.auth.service;

import com.guavapay.ms.auth.entity.User;
import com.guavapay.ms.auth.error.InvalidTokenException;
import com.guavapay.ms.auth.mapper.UserMapper;
import com.guavapay.ms.auth.model.JwtToken;
import com.guavapay.ms.auth.model.LoginRequest;
import com.guavapay.ms.auth.repository.RedisRepository;
import com.guavapay.ms.auth.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.guavapay.ms.auth.security.SecurityContextUtils.extractToken;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public JwtToken login(LoginRequest login) {
        User user = userService.findUserByLogin(login.getUsername(), login.getPassword());
        log.info("User by login, userId: {}", user.getId());

        var jwtToken = tokenProvider.createJwtToken(userMapper.toPrincipal(user));
        redisRepository.save(login.getUsername(), jwtToken);
        log.info("Add token to redis, username: {}", login.getUsername());

        return jwtToken;
    }

    public void logout(String authorizationHeader) {

        //FIXME - Can also parse token and validate user before logout
        String token = extractToken(authorizationHeader, InvalidTokenException::new);
        String username = tokenProvider.getUserName(token);
        redisRepository.delete(username);
    }
}
