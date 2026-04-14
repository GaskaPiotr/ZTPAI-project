package com.github.GaskaPiotr.spring_boot_boilerplate.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
