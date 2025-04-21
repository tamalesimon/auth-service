package com.ticketing.auth_service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.ticketing.auth_service.entity.User;
import com.ticketing.auth_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserbyEmail(String email) {
        return userRepository.findByEmail(email); // Placeholder return statement
    }

}
