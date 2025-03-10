package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {


    private final UserService userService;

    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    private RoleDao roleDao;

    @RequestMapping(value = "/user")
    public String showUserPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        List<Role> roles = roleDao.findRolesByUser(username);
        model.addAttribute("roles", roles);
        boolean isRoleAdmin = false;
        for (Role role : roles) {
            if (Objects.equals(role.getAuthority(), "ROLE_ADMIN")) {
                isRoleAdmin = true;
            } else {
                isRoleAdmin = false;
            }
        }
        model.addAttribute("isRoleAdmin", isRoleAdmin);
        System.out.println("aUT: " + username + " " + user.toString() + " roles: " + roles);
        return "user";
    }
}

