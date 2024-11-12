package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    public AdminController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/show")
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
     //   user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user", user);
        model.addAttribute("message", "Add User");
        return "addUser";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @ModelAttribute("message") String message) {
        userService.saveUser(user);
        String redirectPach;
         if (message.equals("Add User") | message.equals("Update User")) {
             redirectPach = "redirect:/show";
         } else {
             redirectPach = "redirect:/";
         }
        return redirectPach;
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("message", "Update User");
        return "addUser";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/show";
    }
    @RequestMapping(value = "/registration")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("message", "Please enter your registration credentials");
        User user = new User();
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        model.addAttribute("user", user);
        System.out.println(user.toString());
        userService.saveUser(user);
        return "addUser";
    }

}
