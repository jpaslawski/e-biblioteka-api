package com.jpas.ebiblioteka.repository.user;

import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> getUsers() {
        Session session = sessionFactory.getCurrentSession();

        Query<User> query =
                session.createQuery("FROM User AS u WHERE u.role=0 OR u.role=1", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Query<User> query =
                session.createQuery("FROM User WHERE id=:id", User.class);
        query.setParameter("id", id);

        if(query.getResultList().size() > 0) {
            return query.getSingleResult();
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();

        Query<User> query =
                session.createQuery("FROM User WHERE email=:email", User.class);
        query.setParameter("email", email);

        if(query.getResultList().size() > 0) {
            return query.getSingleResult();
        }

        return null;
    }

    @Override
    public UserContact getUserContact(User user) {
        Session session = sessionFactory.getCurrentSession();

        Query<UserContact> query =
                session.createQuery("FROM UserContact WHERE contactOwner=:user", UserContact.class);
        query.setParameter("user", user);

        if(query.getResultList().size() > 0) {
            return query.getSingleResult();
        }

        return null;
    }

    @Override
    public void saveUserAccount(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    public void updateUserAccount(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void saveUserContact(UserContact userContact) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userContact);
    }

    @Override
    public void updateUserContact(UserContact userContact) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userContact);
    }
}
