package ru.kata.spring.boot_security.demo.model;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Builder
@Transactional
@Entity
@Table(name = "users")
public class User implements UserDetails {


//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "id")
//        private int id;

//        @Column(name = "first_name")
//        private String firstName;
//
//        @Column(name = "last_name")
//        private String lastName;
//
//        @Column(name = "email")
//        private String email;

        @Id
        @Column(name = "username")
        private String userName;

        @Column(name = "password")
        private String password;

        @Column(name = "enabled", columnDefinition = "TINYINT")
        private boolean enabled;

//        @Transient
//        private String passwordConfirm;
//
//        @ManyToMany(fetch = FetchType.EAGER)
//        Set <Role> roles;

        public User() {
        }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User(String userName, String password, boolean enabled) {
//            this.id = id;
//            this.firstName = firstName;
//            this.lastName = lastName;
//            this.email = email;
            this.userName = userName;
            this.password = password;
            this.enabled = enabled;
//            this.passwordConfirm = passwordConfirm;
//            this.roles = roles;

        }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

//    public String getUserName() {
//        return userName;
//    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

//    public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public void setFirstName(String firstName) {
//            this.firstName = firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//
//        public void setLastName(String lastName) {
//            this.lastName = lastName;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }

        public void setUsername(String username) {this.userName = userName;}

        public void setPassword(String password) {this.password = password;}

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles;
//    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
            return userName + " " + password;
    }

    public boolean getEnabled() {
            return enabled;
    }
}
