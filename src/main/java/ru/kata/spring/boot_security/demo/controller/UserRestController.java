package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.exceptionhandler.DataInfoHandler;
import ru.kata.spring.boot_security.demo.exceptionhandler.UserWithSuchLoginExist;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/admin/api/users")
public class UserRestController {

    @Autowired
    private EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;
    private UserService userService;
    private RoleDao roleDao;

    public UserRestController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> apiGetAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("id") long id) {
        System.out.println("id= " + id);
        User user = userService.findById(id);
        System.out.println("user= " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataInfoHandler> apiUpdateUser(@PathVariable("id") long id,
                                                         @RequestBody User user,
                                                         BindingResult bindingResult) {
        System.out.println("PUT MAPPING id= " + id + "user= " + user);
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new DataInfoHandler(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new UserWithSuchLoginExist("User with such login Exist");
        }

    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }

    @PostMapping("")
    public ResponseEntity<DataInfoHandler> apiAddNewUser(@Valid @RequestBody User user,
                                                         BindingResult bindingResult) {
        System.out.println("ADD user= " + user);
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new DataInfoHandler(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.saveUser(user, "ROLE_USER");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new UserWithSuchLoginExist("User with such login Exist");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataInfoHandler> apiDeleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new DataInfoHandler("User was deleted"), HttpStatus.OK);
    }
}
