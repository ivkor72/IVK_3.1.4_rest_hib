package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@Entity
@Table (name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private String role;

    @ManyToMany
    Set<User> userName;


    @Override
    public String getAuthority() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    private void savaRole () {

    }
}
