package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<User> getAllUsers() {

        return   em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
       if (user.getId() == 0) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        em.flush();
    }

    @Override
    public User getUser(int id) {
        User user = em.find(User.class, id);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
        em.flush();
    }
}
