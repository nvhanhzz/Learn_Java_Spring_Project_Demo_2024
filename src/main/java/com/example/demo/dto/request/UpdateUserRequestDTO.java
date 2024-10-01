package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateUserRequestDTO implements Serializable {
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @Pattern(regexp = "^(active|inactive)$", message = "Status must be 'active' or 'inactive'")
    private String status;

    @Min(value = 1, message = "Role ID must be greater than or equal to 1")
    private Long roleId;
}