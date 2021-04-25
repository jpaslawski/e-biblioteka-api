package com.jpas.ebiblioteka.controller;

import com.jpas.ebiblioteka.config.SecretKeyGenerator;
import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.UserContact;
import com.jpas.ebiblioteka.entity.request.UserData;
import com.jpas.ebiblioteka.entity.request.UserCredentials;
import com.jpas.ebiblioteka.entity.response.UserResponse;
import com.jpas.ebiblioteka.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/library/users/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable("userId") Integer userId) {
        UserData userData = userService.getUserData(userId);
        if(userData != null) {
            return ResponseEntity.ok(userData);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono użytkownika!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> getUsers() {
        List<UserResponse> users = userService.getUsers();
        if(!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono żadnych użytkowników!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String header) {
        User user = userService.getUserByToken(header);
        if(user != null) {
            return ResponseEntity.ok(user);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono użytkownika!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/contact")
    public ResponseEntity<?> getUserContact(@RequestHeader("Authorization") String header) {
        UserContact userContact = userService.getUserContact(header);
        if(userContact != null) {
            return ResponseEntity.ok(userContact);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono kontaktu!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserData formData) {
        User user = new User(
                formData.getFirstName(),
                formData.getLastName(),
                formData.getEmail(),
                formData.getPassword());

        UserContact userContact = new UserContact(
                formData.getAddress(),
                formData.getCity(),
                formData.getZipCode(),
                formData.getPhoneNumber());

        if(userService.saveUserAccount(user) != null) {
            userContact.setContactOwner(user);
            userService.saveUserContact(userContact);
            return ResponseEntity.ok(user);
        } else {
            JSONObject response = new JSONObject();
            response.put("message", "Ten e-mail jest już powiązany z innym kontem!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials userCredentials) {
        JSONObject response = new JSONObject();

        if(userCredentials.getEmail() != null && userCredentials.getPassword() != null) {
            User user = userService.getUserByEmail(userCredentials.getEmail());

            if(user != null && user.getPassword().equals(userCredentials.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(userCredentials.getEmail())
                        .claim("email", user.getEmail())
                        .claim("role", user.getRole())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                        .signWith(SignatureAlgorithm.HS256, new SecretKeyGenerator().getSecretKey().getBytes(StandardCharsets.UTF_8))
                        .compact();

                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Dane logowania są nieprawidłowe!");
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }
        } else {
            response.put("message", "Dane logowania są niepełne!");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/users/profile")
    public ResponseEntity<?> updateUserPassword(@RequestHeader("Authorization") String header, @RequestBody User updatedUser) {
        User user = userService.getUserByToken(header);
        if(user != null) {
            userService.updateUserPassword(user, updatedUser.getPassword());
            return ResponseEntity.ok(user);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono użytkownika!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Integer userId,  @RequestParam("newRole") String role) {
        User user = userService.getUserById(userId);
        JSONObject response = new JSONObject();

        if(user != null) {
            User updatedUser = userService.updateUserRole(user, role);
            if(updatedUser != null) {
                return ResponseEntity.ok(user);
            }
            response.put("message", "Nie można wykonać tego żądania!");
        } else {
            response.put("message", "Nie znaleziono użytkownika!");
        }

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/users/contact")
    public ResponseEntity<?> updateUserContact(@RequestHeader("Authorization") String header, @RequestBody UserContact userContact) {
        User user = userService.getUserByToken(header);
        if(user != null) {
            userService.updateUserContact(user, userContact);
            return ResponseEntity.ok(userContact);
        }

        JSONObject response = new JSONObject();
        response.put("message", "Nie znaleziono użytkownika!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
