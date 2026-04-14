package com.github.GaskaPiotr.spring_boot_boilerplate.controller;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.LoginRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthService authService;

    @InjectMocks
    AuthController authController;

    @Test
    void login_UserLogsIn_GetsCookieAndHttpOk() {

        // Arrange
        String email = "user@test.com";
        String password = "testPassword";

        LoginRequest request = new LoginRequest(email, password);

        String response = "test-token";

        ResponseCookie cookie = ResponseCookie.from("jwt-token")
                .value(response)
                .domain("localhost")
                .maxAge(Duration.ofSeconds(360))
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        when(authService.login(request)).thenReturn(response);


        // Act

        ResponseEntity<Void> result = authController.login(request);

        // Assert

        // 1. Check the status code
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // 2. Check the header cookie
        assertEquals(cookie.toString(), result.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

        // 3. Check the delegation to service
        verify(authService).login(request);
    }

    @Test
    void login_WrongCredentials_ThrowsException() {

        // Arrange
        String email = "wrong@email.com";
        String password = "wrongpassword";

        LoginRequest badRequest = new LoginRequest(email, password);

        when(authService.login(badRequest))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert

        // 1. Check if authController throws Exception
        assertThrows(BadCredentialsException.class, () -> {
            authController.login(badRequest);
        });

        // 2. Check if authService tried to log in
        verify(authService).login(badRequest);
    }

}