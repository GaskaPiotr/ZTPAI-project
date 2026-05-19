import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  authService = inject(Auth);

  email = '';
  password = '';
  rPassword = '';

  sendRegister() {
    if (this.password != this.rPassword) {
      console.log("Different password");
      alert("Passwords must be the same!")
      return;
    }

    const sendData = {
      email: this.email,
      password: this.password
    };

    console.log("Try to authenticate");

    this.authService.register(sendData)
      .subscribe({
        next: (answer) => {
          console.log("Register successful")
        },
        error: (e) => {
          console.error("Authentication error: ", e);
        }
      })

  }
}
