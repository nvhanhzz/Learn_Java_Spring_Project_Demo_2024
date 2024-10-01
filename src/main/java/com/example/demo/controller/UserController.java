package com.example.demo.controller;

import com.example.demo.enumPackage.Status;
import com.example.demo.dto.request.SaveUserRequestDTO;
import com.example.demo.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.UpdateUserRequestDTO;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User controller", description = "APIs for managing users") // Tag cho toàn bộ controller
public class UserController {
    private final UserService userService;

    @Operation(
            method = "GET",
            summary = "Get a user by ID",
            description = "Retrieve a user's details by providing the user ID"
    )
    @GetMapping("/{userId}")
    public ResponseData<UserResponseDTO> getUser(@PathVariable long userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get user successfully", userService.getUser(userId));
    }

    @Operation(
            method = "POST",
            summary = "Add a new user",
            description = "Create a new user by providing user details"
    )
    @PostMapping("")
    public ResponseData<Long> addUser(@Valid @RequestBody SaveUserRequestDTO userDTO) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "User created successfully", userService.saveUser(userDTO));
    }

    @Operation(
            method = "GET",
            summary = "Get a list of users",
            description = "Retrieve a paginated list of users, with sorting and filtering options"
    )
    @GetMapping("")
    public ResponseData<Page<UserResponseDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get list user successfully", userService.getUsers(page, size, sortBy, sortDirection));
    }

    @Operation(
            method = "PATCH",
            summary = "Update a user",
            description = "Update an existing user's details by providing their user ID"
    )
    @PatchMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable long userId, @Valid @RequestBody UpdateUserRequestDTO userDTO) {
        userService.updateUser(userId, userDTO);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
    }

    @Operation(
            method = "PATCH",
            summary = "Update status of a user",
            description = "Update status of user by providing their user ID"
    )
    @PatchMapping("/{userId}/status/{status}")
    public ResponseData<?> updateUserStatus(@PathVariable long userId, @PathVariable String status) {
        Status.validate(status);
        userService.changeStatus(userId, status);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User status updated successfully");
    }
}