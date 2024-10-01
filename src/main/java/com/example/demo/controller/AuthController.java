package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.ResponseData;
import com.example.demo.dto.response.UserResponseDTO;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth controller", description = "APIs for authentication")
public class AuthController {
    private final AuthService authService;

    @Operation(
            method = "POST",
            summary = "Login",
            description = "Login to system"
    )
    @PostMapping("/login")
    public ResponseData<LoginResponseDTO> addUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return new ResponseData<>(HttpStatus.OK.value(), "Login successfully", authService.login(loginRequestDTO));
    }

    @GetMapping("/information")
    public ResponseData<UserResponseDTO> getInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {
            return new ResponseData<>(HttpStatus.OK.value(), "Get information successfully", authService.getCurrentUser(user));
        }

        return new ResponseData<>(HttpStatus.UNAUTHORIZED.value(), "Get information unsuccessfully");
    }
}