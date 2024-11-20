package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleDao roleDao;
    public AdminController(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @RequestMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, admin!");
        messages.add("All Users:");
        model.addAttribute("messages", messages);
        List<User> allUsers = userService.getAllUsers();
        System.out.println("All Users: " + allUsers);
        model.addAttribute("users", allUsers);
        return "show";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(ModelMap model) {
        User user = new User();
     //
        model.addAttribute("user", user);
        model.addAttribute("message", "Add User");
        return "addUser";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("message") String message) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        String redirectPach;
         if (message.equals("Add User") | message.equals("Update User")) {
             redirectPach = "redirect:/admin";
         } else {
             redirectPach = "redirect:/";
         }
        return redirectPach;
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(@RequestParam("username") String username, Model model) {
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("message", "Update User");
        return "addUser";
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
        model.addAttribute("user", user);
        return "addUser";
    }

    @RequestMapping(value = "/showRoles")
    public String showRolesByUser(@RequestParam("username") String username, ModelMap model) {

     //   String username = (String) model.getAttribute("username");
        System.out.println("****************username: " + username);
        List<Role> roles = roleDao.findRolesByUser(username);
        model.addAttribute("roles", roles);
        model.addAttribute("username", username);
        return "showRoles";
    }

    @RequestMapping(value = "/updateRole")
    public String addRole(@RequestParam("username") String username, @RequestParam("role") String role, ModelMap model) {

        roleDao.saveRole(username, role);
        return "addRole";
    }

}
