package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.persistence.EntityManager;
import java.util.List;


@Controller
public class AdminController {
    @Autowired
    private EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleDao roleDao;

    public AdminController(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    public static class UsersAndRoles {
        private String username;
        private boolean enabled;
        private List<Role> roles;

        UsersAndRoles(String username, boolean enabled, List<Role> roles) {
            this.username = username;
            this.enabled = enabled;
            this.roles = roles;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }
    }

    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
        return "users";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(ModelMap model) {
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("message", "Add User");
        return "addUser";
    }

}
