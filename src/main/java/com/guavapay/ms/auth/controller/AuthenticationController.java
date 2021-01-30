package com.guavapay.ms.auth.controller;

import com.guavapay.ms.auth.model.JwtToken;
import com.guavapay.ms.auth.model.LoginRequest;
import com.guavapay.ms.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtToken login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @GetMapping("/logout")
    public void logout(@RequestHeader @NotNull String authorizationHeader) {
        authenticationService.logout(authorizationHeader);
    }
}
