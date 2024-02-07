package com.mindera.users.service;

import com.mindera.users.domain.User;
import com.mindera.users.exceptions.EmptyFieldException;
import com.mindera.users.exceptions.UserNotFoundException;
import com.mindera.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        if (user.getName().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty())
            throw new EmptyFieldException();
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException();
        return user.get();
    }

    public List<User> getUserByName(String name) {
        List<User> userList = userRepository.findByName(name);

        if (userList.isEmpty()) throw new UserNotFoundException();

        return userList;
    }

    public User update(Integer id, User user) {
        if (user.getName() == null || user.getPassword() == null) throw new EmptyFieldException();
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isEmpty()) throw new UserNotFoundException();

        User user1 = userToUpdate.get();
        if (user.getName() != null) user1.setName(user.getName());
        if (user.getPassword() != null) user1.setPassword(user.getPassword());
        if (user.getEmail() != null) user1.setEmail(user.getEmail());

        userRepository.save(user1);

        return user1;
    }

    public User updatePut(Integer id, String name, String password) {
        Optional<User> user = userRepository.findById(id);
       if (user.isEmpty()) throw new UserNotFoundException();
       User user1 = user.get();

        user1.setName(name);
        user1.setPassword(password);

        return user1;
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public void delete(Integer id) {
        if (id == null) throw new UserNotFoundException();
        if (!userRepository.existsById(id)) throw new UserNotFoundException();
        userRepository.deleteById(id);
    }
}
