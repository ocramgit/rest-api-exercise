package com.mindera.users.controller;

import com.mindera.users.domain.User;
import com.mindera.users.entities.Address;
import com.mindera.users.exceptions.EmptyFieldException;
import com.mindera.users.exceptions.UserNotFoundException;
import com.mindera.users.repository.UserRepository;
import com.mindera.users.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        User user = new User("Marco", "senha", "marco@email.com", new Address());

        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.create(user);

        Assertions.assertNotNull(createdUser);
    }

    @ParameterizedTest
    @ValueSource(strings = {"teste", "", ""})
    void testCreateUser_emptyFieldException(String param) {
        User user = new User("", param, param, new Address());
        Assertions.assertThrows(EmptyFieldException.class, () -> userService.create(user));
    }

    @Test
    void testGetUserByIdSuccess() {
        User user = new User("Marco", "senha", "marco@email.com", new Address());
        when(userRepository.getReferenceById(anyInt())).thenReturn(user);
        Assertions.assertNotNull(user);
    }

    @Test
    void testGetUserById_notFoundException() {
        User user = new User("Marco", "senha", "marco@email.com", new Address());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void testGetUserByNameSuccess() {
        User user = new User("Marco", "senha", "marco@email.com", new Address());
        when(userRepository.findByName(user.getName())).thenReturn(Collections.singletonList(user));
        Assertions.assertNotNull(userService.getUserByName("Marco"));
    }

    @Test
    void testGetUserByName_notFoundException() {
        User user = new User("Marco", "senha", "marco@email.com", new Address());
        when(userRepository.findByName(user.getName())).thenReturn(Collections.singletonList(user));
        Assertions.assertNotNull(userService.getUserByName("Marco"));
    }

    @Test
    void testUpdatePatchUserSuccess() {
            User user = new User(1, "Marco", "senha", "marco@email.com", new Address());

            when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

            User userUpdated = new User();
            userUpdated.setName("Ricardo");

            userService.update(user.getId(), userUpdated);
            assertEquals("Ricardo", user.getName());
            assertEquals("senha", user.getPassword());
            assertEquals("marco@email.com", user.getEmail());
    }

    @Test
    void testUpdatePatchUser_notFound() {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.update(new User().getId(), new User()));
    }
}