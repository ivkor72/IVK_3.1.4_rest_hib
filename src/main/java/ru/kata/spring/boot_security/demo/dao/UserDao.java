package ru.kata.spring.boot_security.demo.dao;





import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

   public List<User> getAllUsers();

   public void saveUser(User user, String message);

   public User getUser(String username);

   public void deleteUser(String username);

   User findByUsername(String username);

   public List<User> findAll();

   public User findUserByUsername(String username);

}
