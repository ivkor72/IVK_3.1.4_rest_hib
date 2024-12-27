package ru.kata.spring.boot_security.demo.model;

import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Entity
@Table(name = "authorities")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
