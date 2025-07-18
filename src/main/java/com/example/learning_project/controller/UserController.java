package com.example.learning_project.controller;

import com.example.learning_project.model.User;
import com.example.learning_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users — Create a user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // GET /users — List all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // GET/ Users with accounts - List all the users with their account
    @GetMapping("/with-accounts")
    public List<User> getAllUsersWithAccounts() {
        return userService.getAllUsers();
    }

    // PUT /users/{id} — Update a user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @DeleteMapping("/all")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

}
