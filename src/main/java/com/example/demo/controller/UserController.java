package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public UserResponseDTO getUsers(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("")
    public long addUser(@RequestBody UserRequestDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @GetMapping("")
    public Page<UserResponseDTO> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return userService.getUsers(page, size, sortBy, sortDirection);
    }
}