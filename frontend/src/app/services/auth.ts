import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';

export interface LoginResponse {
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  userRole: string | null = localStorage.getItem('role');

  login(data: any) {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, data, { withCredentials: true }).pipe(
      tap(response => {
        this.userRole = response.role;
        localStorage.setItem('role', response.role);
      })
    );
  }

  register(data: any) {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  logout() {
    this.userRole = null;
    localStorage.removeItem('role');
  }
}
