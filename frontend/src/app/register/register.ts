import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  http = inject(HttpClient);

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

    this.http.post('http://localhost:8080/api/v1/auth/register', sendData)
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
