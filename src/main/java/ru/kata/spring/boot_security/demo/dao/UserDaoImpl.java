package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager em;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    public UserDaoImpl(BCryptPasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = em.createQuery("from User", User.class).getResultList();
        return usersList;
    }

    @Transactional
    @Override
    public void saveUser(User user, String role) {
        System.out.println("SAVE USER _ user= " + user + "role= " + role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<User> userList = getAllUsers();
        em.merge(user);
        System.out.println("MERGE - user= " + user);
        em.flush();
        roleDao.saveRole(user.getUsername(), role);
    }

    @Override
    public User getUser(String username) {
        User user = em.find(User.class, username);
        return user;
    }

    @Override
    public void deleteUser(long id) {
        List<Role> roles = em.createQuery("from Role", Role.class).getResultList();
        for (Role userR : roles) {
            if (userR.getId().equals(id)) {
                em.remove(userR);
            }
        }
        em.flush();
        User user = em.find(User.class, id);
        em.remove(user);
        em.flush();
    }

    @Override
    public User findById(long id) {
        User user = em.find(User.class, id);
        return user;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public User findUByUsername(String username) {
        return null;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        String role = roleDao.findRoleByIdy(user.getId());
        System.out.println("UPDATE USER role= " + role + "user = " + user);
        saveUser(user, role);
    }
}
