package com.mindera.Users.service;

import com.mindera.Users.domain.User;
import com.mindera.Users.exceptions.UserNotFoundException;
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
        if(user.getName().isEmpty()) throw new UserNotFoundException();
        userRepository.save(user);
        return user;
    }

    public User getUserById(Integer id) {
        if(id == null) throw new UserNotFoundException();
        return userRepository.getReferenceById(id);
    }

    public List<User> getUserByName(String name) {
        if(name == null) throw new UserNotFoundException();
        return userRepository.findByName(name);
    }

    public User update(Integer id, String name, String password) {
        if(id == null) throw new UserNotFoundException();
        User user = userRepository.getReferenceById(id);

        if (name != null) user.setName(name);
        if (password != null) user.setPassword(password);

        userRepository.save(user);

        return user;
    }

    public User updatePut(Integer id, String name, String password) {
        if(id == null) throw new UserNotFoundException();
        User user = userRepository.getReferenceById(id);

        user.setName(name);
        user.setPassword(password);

        return user;
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public void delete(Integer id) {
        if(id == null) throw new UserNotFoundException();
        userRepository.deleteById(id);
    }

    public List<User> getUserByPassword(String password) {
        return userRepository.findByPassword(password);
    }
}
