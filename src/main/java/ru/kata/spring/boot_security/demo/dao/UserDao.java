package ru.kata.spring.boot_security.demo.dao;





import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

   public List<User> getAllUsers();

   public void saveUser(User user);

   public User getUser(int id);

   public void deleteUser(int id);

   User findByUsername(String username);
}
