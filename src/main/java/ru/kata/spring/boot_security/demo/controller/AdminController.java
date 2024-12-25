package ru.kata.spring.boot_security.demo.controller;

import com.mchange.v2.util.DoubleWeakHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.*;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User currentuser = userService.getUser(currentusername);
        List<Role> currentuserroles = roleDao.findRolesByUser(currentusername);
        model.addAttribute("currentusername", currentusername);
        model.addAttribute("currentuserroles", currentuserroles);
        List<String> messages = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();
        List<Role> roles = new ArrayList<>();
        List<UsersAndRoles> usersAndRoles = new ArrayList<>();
        for (User user : allUsers) {
            roles = roleDao.findRolesByUser(user.getUsername());
            usersAndRoles.add(new UsersAndRoles(user.getUsername(), user.getEnabled(), roles));
        }
        model.addAttribute("usersAndRoles", usersAndRoles);
        User user = new User();
        model.addAttribute("user", user);
        Role role = new Role();
        model.addAttribute("role", role);
        return "show";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(ModelMap model) {
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("message", "Add User");
        return "addUser";
    }

    @RequestMapping(value = "/saveUser")
   // public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("message") String message,@ModelAttribute("role") String role) {
        public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("message") String message,@ModelAttribute("role") String role) {
        System.out.println("======SAVE_USER: USER - name - "+user.getUsername()+", enabled - "+user.getEnabled()+" , MESSAGE - "+message+" , ROLE - "+role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user, message, role);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(@RequestParam("user") User user, Model model) {
//        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("message", "Update User");
        return "#exampleModalEdit";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("username") String username, Model model) {
        userService.deleteUser(username);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/registration")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("message", "Please enter your registration credentials");
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        return "addUser";
    }

    @RequestMapping(value = "/showRoles")
    public String showRolesByUser(@RequestParam("username") String username, ModelMap model) {
        modelForRoles(username, model);
        return "showRoles";
    }

    public ModelMap modelForRoles(String username, ModelMap model) {
        List<Role> roles = roleDao.findRolesByUser(username);
        model.addAttribute("roles", roles);
        model.addAttribute("username", username);
        return model;
    }

    @RequestMapping(value = "updateRole")
    public String updateRole(@RequestParam("username") String username, ModelMap model) {
        modelForRoles(username, model);
        return "updateRole";
    }

    @RequestMapping(value = "/deleteRole")
    public String deleteRole(@RequestParam("username") String username, @RequestParam("role") String role, ModelMap model) {
        roleDao.deleteRole(username, role);
        modelForRoles(username, model);
        return "redirect:/updateRole?username=" + username;
    }

    @RequestMapping(value = "/addRoleUser")
    public String addRoleUser(@RequestParam("username") String username, ModelMap model) {
        roleDao.saveRole(username, "ROLE_USER");
        modelForRoles(username, model);
        return "redirect:/show";
    }

    @RequestMapping(value = "/addRoleAdmin")
    public String addRoleAdmin(@RequestParam("username") String username, ModelMap model) {
        roleDao.saveRole(username, "ROLE_ADMIN");
        modelForRoles(username, model);
        return "redirect:/updateRole?username=" + username;
    }
}
