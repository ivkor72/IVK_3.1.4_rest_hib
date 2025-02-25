package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = em.createQuery("from User", User.class).getResultList();
        return usersList;

    }

    @Override
    public void saveUser(User user, String role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<User> userList = getAllUsers();
        for (User u : userList) {
            if (Objects.equals(u.getUsername(), user.getUsername())) {
                em.merge(user);
                em.flush();
                roleDao.saveRole(user.getUsername(), role);
                return;
            }
        }
            em.persist(user);
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
        List <Role> roles = em.createQuery("from Role", Role.class).getResultList();
        for (Role userR : roles) {
            if (userR.getId().equals(id)) {
                em.remove(userR);
            }
        };

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


    @Override
    public User findUserById(long id) {
        User user = em.find(User.class, id);

        return user;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findUByUsername(String username) {
        return null;
    }

    @Override
    public void updateUser(long id) {
        User user = em.find(User.class, id);
        String role = roleDao.findRoleByIdy(user.getId());
        saveUser(user, role);
    }

    @Override
    public List<User> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
