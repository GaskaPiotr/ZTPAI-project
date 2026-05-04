package com.github.GaskaPiotr.spring_boot_boilerplate.dto;

import jakarta.validation.constraints.NotBlank;

public record TicketRequest (
        @NotBlank
        String title,
        @NotBlank
        String description
){}
