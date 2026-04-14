package com.github.GaskaPiotr.spring_boot_boilerplate.dto;

public record UserResponse(
        Long id,
        String email,
        String role
) {}
