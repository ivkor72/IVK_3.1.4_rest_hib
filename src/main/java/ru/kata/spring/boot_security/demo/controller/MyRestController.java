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
    public ModelAndView showAllUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        ModelAndView mav = new ModelAndView("/users");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User currentuser = userService.getUser(currentusername);
        List<Role> currentuserroles = roleDao.findRolesByUser(currentusername);
        model.addAttribute("currentusername", currentusername);
        model.addAttribute("currentuserroles", currentuserroles);
        List<String> messages = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();
        List<Role> roles = new ArrayList<>();
        List<AdminController.UsersAndRoles> usersAndRoles = new ArrayList<>();
        for (User user : allUsers) {
            roles = roleDao.findRolesByUser(user.getUsername());
            usersAndRoles.add(new AdminController.UsersAndRoles(user.getUsername(), user.getEnabled(), roles));
        }
        model.addAttribute("usersAndRoles", usersAndRoles);
        User user = new User();
        model.addAttribute("user", user);
        Role role = new Role();
        model.addAttribute("role", role);
        return mav;
    }


    @GetMapping("/findByUsername")
    public User showUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return user;
    }

    @PutMapping("/users")
    public ModelAndView updateUser(User user, String role) {
        ModelAndView mav = new ModelAndView("/users");
        userService.saveUser(user, role);
//        ModelMap model = new ModelMap();
//        showAllUsers(model);
        return mav;
    }

    @DeleteMapping("/users")
    public ModelAndView deleteUser(String username) {
        ModelAndView mav = new ModelAndView("/users");
        userService.deleteUser(username);
        return mav;
    }
}
