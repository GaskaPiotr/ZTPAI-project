package com.github.GaskaPiotr.spring_boot_boilerplate.service;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.TicketRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.dto.TicketResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Ticket;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import com.github.GaskaPiotr.spring_boot_boilerplate.mapper.TicketMapper;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.TicketRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    public TicketResponse addTicket(TicketRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setStatus("OPEN");
        ticket.setUser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toResponse(savedTicket);
    }
}
