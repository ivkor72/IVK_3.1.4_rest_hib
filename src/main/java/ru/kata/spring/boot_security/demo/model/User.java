package ru.kata.spring.boot_security.demo.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Builder
@Transactional
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Getter
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled", columnDefinition = "TINYINT")
    private boolean enabled;

    public User() {
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(long id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "username= "+username + ", id= "+id+ ",pass= " + password + ", enabled= "+enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
