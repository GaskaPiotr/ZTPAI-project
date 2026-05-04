package com.github.GaskaPiotr.spring_boot_boilerplate.dto;

import java.time.Instant;

public record TicketResponse(
        Long id,
        String title,
        String description,
        String status,
        String authorEmail,
        Instant createdAt
) {}
