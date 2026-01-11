package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have atleast 8 characters")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDateTime updatedAt;
}
