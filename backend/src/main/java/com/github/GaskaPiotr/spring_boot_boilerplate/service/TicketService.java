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

import java.util.List;

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

    public List<TicketResponse> getTickets(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets;
        if ("ADMIN".equalsIgnoreCase(user.getRole().getName())) {
            tickets = ticketRepository.findAll();
        } else {
            tickets = ticketRepository.findByUserId(user.getId());
        }

        return tickets.stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    public void resolveTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus("RESOLVED");
        ticketRepository.save(ticket);
    }
}
