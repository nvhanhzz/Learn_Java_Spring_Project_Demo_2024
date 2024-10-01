package com.example.demo.service;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.User;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    UserResponseDTO getCurrentUser(User user);
}