package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user,  String role);

    public User getUser(String username);

    public void deleteUser(String username);

    public User findById(long id);

    public User findByUsername(String username);

    public void updateUser(User user);
}
