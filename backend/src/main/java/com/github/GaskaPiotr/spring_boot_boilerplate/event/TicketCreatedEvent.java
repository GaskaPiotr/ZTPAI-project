package com.github.GaskaPiotr.spring_boot_boilerplate.event;

public record TicketCreatedEvent (
    Long ticketId,
    String title,
    String authorEmail
) {}
