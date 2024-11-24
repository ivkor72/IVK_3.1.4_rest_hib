package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private EntityManager em;

    @Transactional
    @Override
    public void saveRole(String username, String authority) {
        Role role = new Role(username, authority);
      //  if (Objects.nonNull(em.find(Role.class, authority))) {
        System.out.println("==save role==========username " + username + " role " + authority);
            em.merge(role);
            em.flush();
      //  }
    }

    @Override
    public List<Role> findRolesByUser(String userName) {
        List<Role> roles = new ArrayList<>();
        List<Role> authorities = em.createQuery("from Role", Role.class).getResultList();
        for(Role role : authorities) {
            if(Objects.equals(role.getUsername(), userName)){
                roles.add(role);
            }
            System.out.println("!!!!!!!!!!!!!!!!roles" + roles);
            System.out.println("!!!!!!!!!!!!!!authorities" + authorities);
        }
        return roles;
    }

    @Override
    public void deleteRole(String username, String role) {


        em.createQuery("delete from Role where authority = :role and userName = :username", Role.class).executeUpdate();
    }


//    @Override
//    public Role findByRole(String role) {
//        return null;
//    }
//
//    @Override
//    public List<Role> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public List<Role> findAll(Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public Page<Role> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<Role> findAllById(Iterable<Long> longs) {
//        return List.of();
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Role entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Role> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public <S extends Role> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> List<S> saveAll(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public Optional<Role> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public void flush() {
//
//    }
//
//    @Override
//    public <S extends Role> S saveAndFlush(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> List<S> saveAllAndFlush(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public void deleteAllInBatch(Iterable<Role> entities) {
//
//    }
//
//    @Override
//    public void deleteAllByIdInBatch(Iterable<Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    @Override
//    public Role getOne(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Role getById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public Role getReferenceById(Long aLong) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Role> List<S> findAll(Example<S> example) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Role> List<S> findAll(Example<S> example, Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Role> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Role> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Role> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Role, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }


}
