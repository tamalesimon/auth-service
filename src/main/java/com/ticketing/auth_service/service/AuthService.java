package com.ticketing.auth_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ticketing.auth_service.dto.RegisterClientRequestDTO;
import com.ticketing.auth_service.dto.SigninRequestDTO;
import com.ticketing.auth_service.dto.SigninResponseDTO;
import com.ticketing.auth_service.dto.UserDTO;
import com.ticketing.auth_service.entity.User;
import com.ticketing.auth_service.utils.JwtUtils;

import io.jsonwebtoken.JwtException;

@Service
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Optional<SigninResponseDTO> authenticate(SigninRequestDTO signinRequestDTO) {
        String email = signinRequestDTO.getEmail();
        String password = signinRequestDTO.getPassword();
        Logger logger = org.slf4j.LoggerFactory.getLogger(AuthService.class);
        logger.info("Finding user by email: {}", email);
        logger.info("Finding password: {}", password);
        return userService.findUserbyEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> createSigninResponse(user, email));
    }

    // public

    public Optional<SigninResponseDTO> refreshToken(String refreshToken) {
        String email = jwtUtils.getEmailFromToken(refreshToken);
        String role = jwtUtils.getRoleFromToken(refreshToken);

        return userService.findUserbyEmail(email)
                .filter(user -> user.getRole().name().equals(role))
                .map(user -> createSigninResponse(user, email));
    }

    /**
     * Creates a SigninResponseDTO object with the user's information and JWT
     * tokens.
     *
     * @param user  The authenticated user.
     * @param email The user's email address.
     * @return A SigninResponseDTO object containing the user's information and JWT
     *         tokens.
     */

    private SigninResponseDTO createSigninResponse(User user, String email) {
        String accessToken = jwtUtils.generateToken(email, user.getRole().name(), 30); // 30 minutes expiration
        String refreshToken = jwtUtils.generateToken(email, user.getRole().name(), 120);

        UserDTO userDTO = new UserDTO(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name(),
                user.getStatus().name());

        SigninResponseDTO response = new SigninResponseDTO();
        response.setResponse("success");
        response.setUser(userDTO);
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(30 * 60); // 30 minutes in seconds

        return response;
    }

    public Optional<?> registerClient(RegisterClientRequestDTO registerClientRequestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerClient'");
    }

    public boolean validateToken(String token) {
        try {
            jwtUtils.verifyToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
