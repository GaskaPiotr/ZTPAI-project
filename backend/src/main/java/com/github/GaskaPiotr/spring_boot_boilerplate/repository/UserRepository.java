package com.github.GaskaPiotr.spring_boot_boilerplate.repository;

import com.github.GaskaPiotr.spring_boot_boilerplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
