package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin
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
        public List<User> apiGetAllUsers() {
            List<User> users = userService.getAllUsers();
            System.out.println(users);
            return users;
        }










//    @GetMapping("/findByUsername")
//    public User showUserByUsername(String username) {
//        User user = userService.findByUsername(username);
//        return user;
//    }
//
//    @PutMapping("/users")
//    public ModelAndView updateUser(User user, String role) {
//        ModelAndView mav = new ModelAndView("/users");
//        userService.saveUser(user, role);
////        ModelMap model = new ModelMap();
////        showAllUsers(model);
//        return mav;
//    }
//
//    @DeleteMapping("/users")
//    public ModelAndView deleteUser(String username) {
//        ModelAndView mav = new ModelAndView("/users");
//        userService.deleteUser(username);
//        return mav;
//    }
}
