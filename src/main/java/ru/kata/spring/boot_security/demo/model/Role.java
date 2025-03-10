package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "authorities")
public class Role implements GrantedAuthority {

    @Id
    @PrimaryKeyJoinColumn(name = "users.id")
    @Column(name = "id")
    private long id;

    public Role() {

    }

    public void setId(long id) {
        this.id = id;
    }

    @Getter
    @Column(name = "username")
    private String userName;

    @Column(name = "authority")
    private String authority;

    public Role(String userName, String authority) {
        this.userName = userName;
        this.authority = authority;
    }

    public Role(long id, String userName, String authority) {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Role(Long id, String userName, String authority) {
        this.id = id;
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
