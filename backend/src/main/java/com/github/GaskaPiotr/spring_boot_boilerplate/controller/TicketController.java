package com.github.GaskaPiotr.spring_boot_boilerplate.controller;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.TicketRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.dto.TicketResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> addTicket(
            @Valid @RequestBody TicketRequest request,
            Principal principal
            ) {
        TicketResponse response = ticketService.addTicket(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets(Principal principal) {
        List<TicketResponse> tickets = ticketService.getTickets(principal.getName());
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("{id}/resolve")
    public ResponseEntity<Void> changeTicketStatus(@PathVariable Long id) {
        ticketService.resolveTicket(id);
        return ResponseEntity.ok().build();
    }
}
