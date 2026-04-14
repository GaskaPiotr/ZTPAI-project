package com.github.GaskaPiotr.spring_boot_boilerplate.service;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.LoginRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.dto.RegisterRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Role;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import com.github.GaskaPiotr.spring_boot_boilerplate.exception.RoleNotFoundException;
import com.github.GaskaPiotr.spring_boot_boilerplate.exception.UserAlreadyExistsException;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.RoleRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.UserRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtService jwtService;
    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void login_UserExistsAndPassCorrect_AuthenticatesSuccessfully() {

        // Arrange
        String email = "user@example.com";
        String password = "123456";
        LoginRequest request = new LoginRequest(email, password);

        User testUser = new User(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        when(jwtService.generateToken(testUser)).thenReturn("fake-jwt-token");

        // Act
        authService.login(request);

        // Assert

        ArgumentCaptor<UsernamePasswordAuthenticationToken> captor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(authenticationManager).authenticate(captor.capture());

        assertEquals(email, captor.getValue().getPrincipal());
        assertEquals(password, captor.getValue().getCredentials());

        verify(userRepository).findByEmail(email);
    }

    @Test
    void login_WrongPassword_ThrowException() {

        // Arrange

        String email = "good@email.com";
        String password = "wrongpassword";

        LoginRequest request = new LoginRequest(email, password);

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert

        // 1. Check if login throws an exception
        assertThrows(BadCredentialsException.class, () -> authService.login(request));

        // 2. Check if database was ever touched
        verify(userRepository, never()).findByEmail(any());
    }

    @Test
    void login_UserDeletedButAuthPasses_ThrowException() {

        // Arrange

        String email = "ghost@test.com";

        String password = "testpassword";

        LoginRequest request = new LoginRequest(email, password);

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        // Act & Assert

        assertThrows(UsernameNotFoundException.class, () ->  authService.login(request));

    }

    @Test
    void login_JWTGenerationFails_ThrowException() {

        // Arrange

        String email = "test@test.com";
        String password = "testpassword";

        LoginRequest request = new LoginRequest(email, password);

        User user = new User();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        when(jwtService.generateToken(any()))
                .thenThrow(new RuntimeException("Missing key"));

        // Act & Assert

        assertThrows(RuntimeException.class, () -> authService.login(request));

    }


    @Test
    void register_EmailNotUsed_RegisteredSuccessfully() {

        // Arrange

        String email = "example@test.com";
        String password = "passwordExample";
        String encodedPassword = "encodedPassword";

        RegisterRequest request = new RegisterRequest(email, password);

        Role role = new Role("USER");

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        // Intellij was yelling that Argument ".encode(password)" might be null, so I used doReturn.
        // when().thenReturn() calls the method but doReturn don't that's why it is working.
        doReturn(encodedPassword).when(passwordEncoder).encode(password);

        when(roleRepository.findByName("USER"))
                .thenReturn(Optional.of(role));


        // Act

        authService.register(request);


        // Assert

        // ArgumentCaptor for the User class
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        // Capture the user that was passed
        verify(userRepository).save(userArgumentCaptor.capture());

        // SavedUser
        User savedUser = userArgumentCaptor.getValue();

        // Assert savedUser parameters
        assertEquals(email, savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals(role, savedUser.getRole());


        // Check if findByEmail was called
        verify(userRepository).findByEmail(email);

    }

    @Test
    void register_EmailUsed_ThrowException() {

        // Arrange
        String email = "usedMail@example.com";

        String password = "testPassword";

        RegisterRequest request = new RegisterRequest(email, password);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(new User(email, password)));

        // Act & Assert

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void register_UserRoleNotFound_ThrowException() {

        // Arrange

        String email = "email@example.com";
        String password = "passwordExample";

        RegisterRequest request = new RegisterRequest(email, password);

        when(roleRepository.findByName("USER"))
                .thenThrow(new RoleNotFoundException("User role not found"));

        // Act & Assert

        assertThrows(RoleNotFoundException.class, () -> authService.register(request));
    }
}