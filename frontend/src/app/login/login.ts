import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  authService = inject(Auth);

  email = '';
  password = '';

  sendLogin() {

    const sendData = {
      email: this.email,
      password: this.password
    };

    console.log("Try to authenticate");

    this.authService.login(sendData)
      .subscribe({
        next: (answer) => {
          console.log("Login successful")
        },
        error: (e) => {
          console.error("Authentication error: ", e);
        }
      })

  }
}
