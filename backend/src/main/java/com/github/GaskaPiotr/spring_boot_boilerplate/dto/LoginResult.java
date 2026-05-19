package com.github.GaskaPiotr.spring_boot_boilerplate.dto;

public record LoginResult(
        String token,
        LoginResponse responseBody
) {}
