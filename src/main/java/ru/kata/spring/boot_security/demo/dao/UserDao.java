package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserDao {

    public List<User> getAllUsers();

    public void saveUser(User user, String role);

    public User getUser(String username);

    public void deleteUser(long id);

    public User findById(long id);

    public User findUByUsername(String username);

    public void updateUser(User user);
}
