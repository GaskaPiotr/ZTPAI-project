import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ticket } from '../services/ticket';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tickets.html',
  styleUrl: './tickets.scss',
})
export class Tickets implements OnInit {
  ticketService = inject(Ticket);
  authService = inject(Auth);
  tickets: Ticket[] = [];

  ngOnInit() {
    this.loadTickets();
  }

  loadTickets() {
    this.ticketService.getTickets().subscribe({
      next: (data) => this.tickets = data,
      error: (err) => console.error("Error loading tickets", err)
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
