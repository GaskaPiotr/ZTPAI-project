import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Register } from './register/register'
import { Tickets } from './tickets/tickets'

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'tickets', component: Tickets }
];
