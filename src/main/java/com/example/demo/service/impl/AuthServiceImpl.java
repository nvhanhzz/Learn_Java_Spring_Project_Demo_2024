package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.enumPackage.Status;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo.security.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager; // Ensure it is final for constructor injection

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        User user = userRepository.findByUsername(loginRequestDTO.getUsername());
        if (user == null) {
            throw new UnauthorizedException("Login failed! User not found.");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

            // Authentication with the authentication manager
            authenticationManager.authenticate(authenticationToken);

            if (!user.getStatus().equals(Status.ACTIVE.getValue())) {
                throw new UnauthorizedException("Login failed! User not found.");
            }

            // Generate and return the token if authentication was successful
            return LoginResponseDTO.builder().token(jwtTokenUtil.generateToken(user)).build();

        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}