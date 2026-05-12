package com.github.GaskaPiotr.spring_boot_boilerplate.listener;

import com.github.GaskaPiotr.spring_boot_boilerplate.event.TicketCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @EventListener
    public void handleTicketCreatedEvent(TicketCreatedEvent event) {
        try {
            Thread.sleep(3000);
        } catch(InterruptedException exception) {
            exception.printStackTrace();
        }

        System.out.println();
        System.out.println("Email send to admin");
        System.out.println("New ticket " + event.ticketId() + " created");
        System.out.println("Title " + event.title());
        System.out.println("From " + event.authorEmail() + " user");
        System.out.println();
    }
}
