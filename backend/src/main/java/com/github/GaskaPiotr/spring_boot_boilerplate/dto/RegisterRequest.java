package com.github.GaskaPiotr.spring_boot_boilerplate.dto;

import com.github.GaskaPiotr.spring_boot_boilerplate.annotation.StrongPassword;
import jakarta.validation.constraints.Email;

public record RegisterRequest(
        @Email
        String email,

        @StrongPassword
        String password
) {}
