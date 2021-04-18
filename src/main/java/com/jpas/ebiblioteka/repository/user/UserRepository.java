package com.jpas.ebiblioteka.repository.user;

import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;

public interface UserRepository {

    User getUserById(Integer id);

    User getUserByEmail(String email);

    UserContact getUserContact(User user);

    void saveUserAccount(User user);

    void updateUserAccount(User user);

    void saveUserContact(UserContact userContact);

    void updateUserContact(UserContact userContact);
}
