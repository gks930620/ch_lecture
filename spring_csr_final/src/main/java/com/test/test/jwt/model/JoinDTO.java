package com.test.test.jwt.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be 4-20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100, message = "Password must be at least 4 characters")
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Nickname is required")
    @Size(min = 2, max = 20, message = "Nickname must be 2-20 characters")
    private String nickname;
}