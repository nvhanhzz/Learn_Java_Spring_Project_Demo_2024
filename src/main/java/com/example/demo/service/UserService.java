package com.example.demo.service;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    long saveUser(UserRequestDTO user);

    void updateUser(long userId, UserRequestDTO user);

    void changeStatus(long userId, String userStatus);

    void deleteUser(long userId);

    UserResponseDTO getUser(long userId);

    Page<UserResponseDTO> getUsers(long page, long size, String sortBy, String sortDirection);
}