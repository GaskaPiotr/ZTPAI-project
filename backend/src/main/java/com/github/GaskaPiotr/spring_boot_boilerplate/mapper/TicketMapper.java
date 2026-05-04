package com.github.GaskaPiotr.spring_boot_boilerplate.mapper;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.TicketResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {
    TicketResponse toResponse(Ticket ticket);
}
