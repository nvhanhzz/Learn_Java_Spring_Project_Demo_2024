package com.example.demo.controller;

import com.example.demo.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.UserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User controller")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseData<UserResponseDTO> getUsers(@PathVariable long userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get user successfully", userService.getUser(userId));
    }

    @PostMapping("")
    public ResponseData<Long> addUser(@Valid  @RequestBody UserRequestDTO userDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "User created successfully", userService.saveUser(userDTO));
    }

    @GetMapping("")
    public ResponseData<Page<UserResponseDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list user successfully", userService.getUsers(page, size, sortBy, sortDirection));
    }
}