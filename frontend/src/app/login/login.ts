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

    this.http.post('http://localhost:8080/api/login', sendData)
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
