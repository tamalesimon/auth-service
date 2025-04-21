package com.ticketing.auth_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketing.auth_service.dto.RegisterClientRequestDTO;
import com.ticketing.auth_service.dto.SigninRequestDTO;
import com.ticketing.auth_service.dto.SigninResponseDTO;
import com.ticketing.auth_service.service.AuthService;
import com.ticketing.auth_service.service.UserService;

import lombok.extern.log4j.Log4j2;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/v1/auth")
@Log4j2
public class AuthController {



    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    
    @PostMapping("/token")
    public ResponseEntity<SigninResponseDTO> authenticate(@RequestBody SigninRequestDTO signinRequestDTO) {
        return authService.authenticate(signinRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build());
    }

    @PostMapping("/register-client")
    public ResponseEntity<?> registerClient(@RequestBody RegisterClientRequestDTO registerClientRequestDTO) {
        return authService.registerClient(registerClientRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SigninResponseDTO> refreshToken(@RequestBody String refreshToken) {
        return authService.refreshToken(refreshToken)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build());
    }

    @GetMapping("/validate-client")
    public ResponseEntity<Void> validateClient(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);

        return authService.validateToken(token)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
