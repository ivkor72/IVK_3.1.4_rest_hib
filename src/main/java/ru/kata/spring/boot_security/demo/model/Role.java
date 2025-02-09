package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Transactional
@Entity
@Table(name = "authorities")
public class Role implements GrantedAuthority {


    @Id
    @PrimaryKeyJoinColumn(name = "users.id")
    @Column(name = "id")
    private long id;

    public void setId(long id) {
        this.id = id;
    }


    @Column (name = "userName")
    private String userName;



    @Column(name = "authority")
    private String authority;


    public Role(String username, String authority) {

    }

    public Role(long id, String username, String authority) {
    }

    public String getUserName() {
        return userName;
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
