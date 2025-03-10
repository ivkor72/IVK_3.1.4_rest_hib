package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

public interface RoleDao {

    public void saveRole(String username, String authority);

    public List<Role> findRolesByUser(String userName);

    public void deleteRole(String username, String authority);

    public String findRoleByUsername(String username);

    public String findRoleByIdy(long id);
}
