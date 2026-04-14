package com.github.GaskaPiotr.spring_boot_boilerplate.service;

import com.github.GaskaPiotr.spring_boot_boilerplate.dto.LoginRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.dto.RegisterRequest;
import com.github.GaskaPiotr.spring_boot_boilerplate.dto.RegisterResponse;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Role;
import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import com.github.GaskaPiotr.spring_boot_boilerplate.exception.RoleNotFoundException;
import com.github.GaskaPiotr.spring_boot_boilerplate.exception.UserAlreadyExistsException;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.RoleRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.repository.UserRepository;
import com.github.GaskaPiotr.spring_boot_boilerplate.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RoleRepository roleRepository
        ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtService.generateToken(user);
    }
    
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {

            // --- WARNING ---
            // VULNERABLE TO User Enumeration Attacks
            // --- WARNING ---

            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = new User();

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Role role = roleRepository.findByName("USER")
                        .orElseThrow(() -> new RoleNotFoundException("User role not found"));
        user.setRole(role);
        userRepository.save(user);

        // --- WARNING ---
        // VULNERABLE TO User Enumeration Attacks
        // --- WARNING ---

        return new RegisterResponse("User registered successfully");
    }
}
