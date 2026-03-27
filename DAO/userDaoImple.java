package com.taskmanager.taskmanager.DAO;

import com.taskmanager.taskmanager.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class userDaoImple implements userDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public userDaoImple(EntityManager em) {
        this.entityManager = em;

    }
    @Override
    public List<Users> findALlUsers() {
      TypedQuery<Users> theQuery=  entityManager.createQuery("SELECT  u from Users u", Users.class);
      return theQuery.getResultList();
    }
    @Override
    public Users  findUserByName(String name){
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u LEFT JOIN FETCH u.authorities WHERE u.username = :username", Users.class);
        query.setParameter("username", name);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void saveUser(Users user){

        entityManager.persist(user);
        entityManager.flush(); 
        System.out.println("Persisted user with username: " + user.getUserName());
    }

    @Override

    public Users updateUser(Users user){

        return entityManager.merge(user);
    }


    @Override

    public Users findUserById(int id){
        return entityManager.find(Users.class, id);


    }
    @Override
    public void deleteUser(int id) {
        Users user = entityManager.find(Users.class, id);
        entityManager.remove(user);
    }

}