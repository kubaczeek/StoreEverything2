package com.example.storeeverything.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;

    @NotBlank(message = "Name can't be empty")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String name;

    @NotBlank(message = "Password can't be empty")
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}
