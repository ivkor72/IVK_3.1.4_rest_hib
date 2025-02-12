package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/admin/api")
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
        public ResponseEntity <List<User>> apiGetAllUsers() {
            List<User> users = userService.getAllUsers();
            System.out.println(users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }

    @GetMapping("/users/{id}")
    public ResponseEntity <User> apiGetOneUser(@PathVariable("id") long id) {
        System.out.println("id= "+ id);
        User user = userService.findById(id);
        System.out.println("user= " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> apiUpdateUser(@PathVariable("id") long id,
                                                         @RequestBody User user) {
        String role = "ROLE_ADMIN";
//        User user = userService.findByUsername(username);
        userService.saveUser(user, role);
        System.out.println("===putmapping==apiUpdateUser===id="+id+" user="+user+" role="+role);
            return new ResponseEntity<>(user, HttpStatus.OK);

    }



//    @GetMapping("/findByUsername")
//    public User showUserByUsername(String username) {
//        User user = userService.findByUsername(username);
//        return user;
//    }


//
//    @PutMapping("/users/{username}")
//    public ResponseEntity<User> updateUser(User user, String role) {
//        role = "ROLE_ADMIN";
//        userService.saveUser(user, role);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/users")
//    public ModelAndView deleteUser(String username) {
//        ModelAndView mav = new ModelAndView("/users");
//        userService.deleteUser(username);
//        return mav;
//    }
}
