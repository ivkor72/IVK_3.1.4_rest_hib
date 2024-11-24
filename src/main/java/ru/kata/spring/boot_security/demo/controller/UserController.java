package ru.kata.spring.boot_security.demo.controller;

import jdk.internal.access.JavaLangInvokeAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.configs.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.configs.MyUserPrincipal;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
public class UserController {



    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

        @GetMapping("/user")
        public String showUserPage(@AuthenticationPrincipal UserDetails userDetails) {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);
            System.out.println("%%%%%%%%%%%%current user: " + currentUser);
            return "user";
        }

//    @GetMapping(value = "/user")
//    public String showUserPage(Model model) {
////        String username;
////        userService.getUser(username);
//        return "user";
    }

