package com.jpas.ebiblioteka.service.user;

import com.jpas.ebiblioteka.config.SecretKeyGenerator;
import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;
import com.jpas.ebiblioteka.entity.request.UserData;
import com.jpas.ebiblioteka.repository.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    @Override
    @Transactional
    public UserData getUserData(Integer id) {
        User user = userRepository.getUserById(id);
        if(user != null) {
            UserContact userContact = userRepository.getUserContact(user);
            UserData userData = new UserData(user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    null,
                    userContact.getAddress(),
                    userContact.getCity(),
                    userContact.getZipCode(),
                    userContact.getPhoneNumber());

            return userData;
        }
        return null;
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    @Transactional
    public User getUserByToken(String header) {
        String secretKey = new SecretKeyGenerator().getSecretKey();
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace("Bearer ", ""));

        String email = claims.getBody().get("email").toString();

        return getUserByEmail(email);
    }

    @Override
    @Transactional
    public UserContact getUserContact(String header) {
        User user = getUserByToken(header);
        if(user != null) {
            return userRepository.getUserContact(user);
        }
        return null;
    }

    @Override
    @Transactional
    public User saveUserAccount(User user) {
        User optionalUser = getUserByEmail(user.getEmail());
        if(optionalUser == null) {
            user.setId(0);
            userRepository.saveUserAccount(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveUserContact(UserContact userContact) {
        userRepository.saveUserContact(userContact);
    }

    @Override
    @Transactional
    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        userRepository.updateUserAccount(user);
    }

    @Override
    @Transactional
    public void updateUserContact(User user, UserContact userContact) {
        userContact.setContactOwner(user);

        if(user.getUserContact() != null) {
            userContact.setId(user.getUserContact().getId());
            userRepository.updateUserContact(userContact);
        } else {
            userContact.setId(0);
            userRepository.saveUserContact(userContact);
        }
    }
}
