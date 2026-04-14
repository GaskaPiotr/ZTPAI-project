package com.github.GaskaPiotr.spring_boot_boilerplate.dto;


import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

    @NotBlank(message="Email can't be empty!")
    String email,

    @NotBlank(message="Password can't be empty!")
    String password
) {}
