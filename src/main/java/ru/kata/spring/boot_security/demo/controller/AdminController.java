package ru.kata.spring.boot_security.demo.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String showAllUsers(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello, admin!");
        messages.add("All Users:");
        model.addAttribute("messages", messages);
        List<User> allUsers = userService.getAllUsers();
        System.out.println("All Users: " + allUsers);
        model.addAttribute("users", allUsers);
        return "admin";
    }

    @RequestMapping(value = "/admin/addUser")
    public String addUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", "Add User");
        return "addUser";
    }

    @RequestMapping(value = "/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("message", "Update User");
        return "addUser";
    }

    @RequestMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
