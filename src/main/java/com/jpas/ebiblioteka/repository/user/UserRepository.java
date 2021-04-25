package com.jpas.ebiblioteka.repository.user;

import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User getUserById(Integer id);

    User getUserByEmail(String email);

    UserContact getUserContact(User user);

    void saveUserAccount(User user);

    void updateUserAccount(User user);

    void saveUserContact(UserContact userContact);

    void updateUserContact(UserContact userContact);
}
