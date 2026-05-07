import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Ticket } from '../services/ticket';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tickets.html',
  styleUrl: './tickets.scss',
})
export class Tickets implements OnInit {
  ticketService = inject(Ticket);
  authService = inject(Auth);
  cdr = inject(ChangeDetectorRef);
  tickets: Ticket[] = [];
  newTicketTitle = '';
  newTicketDescription = '';

  ngOnInit() {
    this.loadTickets();
  }

  loadTickets() {
    this.ticketService.getTickets().subscribe({
      next: (data) =>  {
        this.tickets = data,
        this.cdr.detectChanges();
      },
      error: (err) => console.error("Error loading tickets", err)
    });
  }

  createTicket() {
    if (!this.newTicketTitle.trim() || !this.newTicketDescription.trim()) {
      alert("Title and description cannot be empty!");
      return;
    }

    const newTicket = {
      title: this.newTicketTitle,
      description: this.newTicketDescription
    };

    this.ticketService.addTicket(newTicket).subscribe({
      next: () => {
        console.log("Ticket created successfully!");
        this.newTicketTitle = '';
        this.newTicketDescription = '';
        this.loadTickets();
      },
      error: (err) => console.error("Error creating ticket", err)
    });
  }

  resolve(id: number) {
    this.ticketService.resolveTicket(id).subscribe({
      next: () => {
        console.log("Ticket resolved!");
        this.loadTickets();
      },
      error: (err) => console.error("Error resolving ticket", err)
    });
  }

  get isAdmin(): boolean {
    return this.authService.userRole === 'ADMIN';
  }
}
