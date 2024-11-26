package ru.kata.spring.boot_security.demo.model;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Entity
@Table(name = "authorities")
public class Role implements GrantedAuthority {

    @Id
    @PrimaryKeyJoinColumn(name = "users.username")
    @Column(name = "username")
    private String userName;

    @Setter
    @Column(name = "authority")
    private String authority;

    public Role() {

    }

    public Role(String userName, String authority) {
        this.userName = userName;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getUsername() {
        return userName;
    }

    @Override
    public String toString() {
        return authority;
    }

}
