package com.github.GaskaPiotr.spring_boot_boilerplate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user_role")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Collection<User> user;

    public Role(String name) {
        this.name = name;
    }
}