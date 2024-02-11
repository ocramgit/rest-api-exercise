package com.mindera.users.controller;

import com.mindera.users.domain.User;
import com.mindera.users.service.UserService;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok().body(userService.getUserByName(name));
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PatchMapping
    public ResponseEntity<User> update(@RequestParam(required = false) Integer id , @RequestBody User user) {
        return ResponseEntity.ok().body(userService.update(id, user));
    }

    @PutMapping
    public ResponseEntity<User> updatePut(@RequestParam(required = false) Integer id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updatePut(id, user.getName(), user.getPassword()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUserList());
    }
}
