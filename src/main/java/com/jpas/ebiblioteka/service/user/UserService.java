package com.jpas.ebiblioteka.service.user;

import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;

public interface UserService {

    User getUserById(Integer id);

    User getUserByEmail(String email);

    User getUserByToken(String header);

    UserContact getUserContact(String header);

    User saveUserAccount(User user);

    void saveUserContact(UserContact userContact);

    void updateUserPassword(User user, String password);

    void updateUserContact(User user, UserContact userContact);
}
