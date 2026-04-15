import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  http = inject(HttpClient);

  email = '';
  password = '';
  rPassword = '';

  sendLogin() {
    console.log("Try to login with:");
  }
}
