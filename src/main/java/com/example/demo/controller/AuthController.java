package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequestDTO;
import com.example.demo.dto.response.LoginResponseDTO;
import com.example.demo.dto.response.ResponseData;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}