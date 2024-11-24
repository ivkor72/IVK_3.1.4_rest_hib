package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface RoleDao {

    public void saveRole(String username, String authority);

    public List<Role> findRolesByUser(String userName);

    public void deleteRole(String username, String authority);
}
