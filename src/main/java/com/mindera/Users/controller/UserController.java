package com.mindera.Users.controller;

import com.mindera.Users.domain.User;
import com.mindera.Users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(201).body(userService.create(user));
    }

    @GetMapping("{name}")
    public List<User> getUserByName(@RequestParam(required = false) String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("{id}")
    public User getUserById(@RequestParam(required = false) Integer id) {
        return userService.getUserById(id);
    }

    @PatchMapping
    public ResponseEntity<User> update(@RequestParam(required = false) Integer id , @RequestBody User user) {
        return ResponseEntity.status(200).body(userService.update(id, user.getName(), user.getPassword()));
    }

    @PutMapping
    public ResponseEntity<User> updatePut(@RequestParam(required = false) Integer id, @RequestBody User user) {
        return ResponseEntity.status(200).body(userService.updatePut(id, user.getName(), user.getPassword()));
    }

}
