package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleDao roleDao;

    public MyRestController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return allUsers;
    }


    @GetMapping("/findByUsername")
    public User showUserByUsername(@RequestBody String username) {
        User user = userService.findByUsername(username);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user, String role) {
        userService.saveUser(user, role);
        return user;
    }

}
