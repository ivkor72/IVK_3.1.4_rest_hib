package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Repository
public class RoleDaoImpl implements RoleDao {


    @Autowired
    private EntityManager em;

    private UserService userService;



@Transactional

@Override
public void saveRole(String username, String authority) {
    System.out.println("SAVE ROLE: Username: " + username + " Authority: " + authority);
    Role role = new Role(username, authority);
    System.out.println("SAVE ROLE: role " + role);
//        if (authority == null || authority.isEmpty()) {
//            return;
//        }
//
        List<Role> roles = findRolesByUser(username);
        for (Role role1 : roles) {
            if (Objects.equals(role1.getAuthority(), authority)) {
                return;
            }
        }

    em.persist(role);
    //   em.merge(role);
    em.flush();
}

    @Override
    public String findRoleByUsername(String username) {
        return (em.find(Role.class, username)).getAuthority();
    }

    @Override
    public String findRoleByIdy(long id) {
        return (em.find(Role.class, id)).getAuthority();
    }

    @Override
    public List<Role> findRolesByUser(String userName) {
        List<Role> roles = new ArrayList<>();
        List<Role> authorities = em.createQuery("from Role", Role.class).getResultList();
        for (Role role : authorities) {
            if (Objects.equals(role.getUsername(), userName)) {
                roles.add(role);
            }
        }

        return roles;
    }

    @Override
    public void deleteRole(String username, String role) {
        em.createQuery("delete from Role where authority = :role and userName = :username", Role.class).executeUpdate();
    }
}
