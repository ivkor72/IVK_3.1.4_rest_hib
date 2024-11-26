package ru.kata.spring.boot_security.demo.controller;

import jdk.internal.access.JavaLangInvokeAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@Controller
public class UserController {



    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

        @RequestMapping(value = "/user")
        public String showUserPage(Model model) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUser(username);

            model.addAttribute("user", user);
      //      boolean enabled = userDetails.isEnabled();
            System.out.println("aUT: " + username + " " + user.toString() );
            return "user";
        }

//    @GetMapping(value = "/user")
//    public String showUserPage(Model model) {
////        String username;
////        userService.getUser(username);
//        return "user";
    }

