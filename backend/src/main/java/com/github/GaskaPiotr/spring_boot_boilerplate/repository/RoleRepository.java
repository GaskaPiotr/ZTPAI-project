package com.github.GaskaPiotr.spring_boot_boilerplate.repository;

import com.github.GaskaPiotr.spring_boot_boilerplate.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
