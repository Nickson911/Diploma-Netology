package ru.netology.netologydiploma.controller;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.netologydiploma.model.AuthRequest;
import ru.netology.netologydiploma.model.AuthResponse;
import ru.netology.netologydiploma.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private static final Logger log = Logger.getLogger(AuthController.class);

    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        log.info("New authRequest");
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        log.info("Logout request received");
        return ResponseEntity.ok("Successfully logged out");
    }

}