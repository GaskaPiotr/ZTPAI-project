import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Ticket {
  id: number;
  title: string;
  description: string;
  status: string;
  authorEmail?: string;
}

@Injectable({
  providedIn: 'root',
})
export class Ticket {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/tickets';

  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.apiUrl, { withCredentials: true });
  }

  resolveTicket(id: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${id}/resolve`, {}, { withCredentials: true });
  }

  addTicket(ticket: { title: string; description: string }): Observable<Ticket> {
    return this.http.post<Ticket>(this.apiUrl, ticket, { withCredentials: true });
  }
}
