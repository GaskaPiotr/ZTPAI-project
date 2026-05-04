package com.github.GaskaPiotr.spring_boot_boilerplate.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    @PostMapping
    public void addTicket() {

    }

    @GetMapping
    public List<> getTickets() {

    }

    @PatchMapping("{id}/resolve")
    public void changeTicketStatus() {

    }
}
