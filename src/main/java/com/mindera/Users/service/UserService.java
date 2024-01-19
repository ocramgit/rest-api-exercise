package com.mindera.Users.service;

import com.mindera.Users.domain.User;
import com.mindera.Users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        userRepository.save(user);
        return user;
    }

    public User getUserById(Integer id) {
        return userRepository.getReferenceById(id);
    }

    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User update(Integer id, String name, String password) {
        User user = userRepository.getReferenceById(id);

        if (name != null) user.setName(name);
        if (password != null) user.setPassword(password);

        return user;
    }

    public User updatePut(Integer id, String name, String password) {
        User user = userRepository.getReferenceById(id);

        user.setName(name);
        user.setPassword(password);

        userRepository.save(user);

        return user;
    }

}
