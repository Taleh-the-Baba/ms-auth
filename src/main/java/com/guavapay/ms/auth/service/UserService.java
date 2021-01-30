package com.guavapay.ms.auth.service;

import com.guavapay.ms.auth.entity.User;
import com.guavapay.ms.auth.error.UserNotFoundException;
import com.guavapay.ms.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    User findUserByLogin(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password)
                .orElseThrow(UserNotFoundException::new);
    }
}
