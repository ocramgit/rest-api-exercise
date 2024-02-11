package com.mindera.users.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.users.domain.User;
import com.mindera.users.entities.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateUser_userCreated_success() throws Exception {
        User user = new User("Marco", "Teste123", "marco@email.com", new Address());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateUserUsingBlankNameField__notCreated_fail() throws Exception {
        User user = new User("", "Teste", "email@teste", new Address());

        ObjectMapper objectMapper = new ObjectMapper();
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserUsingBlankPasswordField_notCreated_fail() throws Exception {
        User user = new User("Marco", "");

        ObjectMapper objectMapper = new ObjectMapper();
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetUserById_found_success() throws Exception {
        when(userRepository.findById(8000)).thenReturn(Optional.of(new User()));

        mockMvc.perform(get("/user/{id}", 8000))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserById_notFound_badRequest() throws Exception {
        when(userRepository.getReferenceById(8000)).thenReturn(null);

        mockMvc.perform(get("/user/{id}", 7000))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetUserByName_found_success() throws Exception {
        User user = new User("Marco", "senhafacil");

        when(userRepository.findByName("Marco")).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/user")
                        .param("name", "Marco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Marco"));
    }

    @Test
    void testGetUserByName_userNotFound_failed() throws Exception {
        when(userRepository.findByName("Marcovski")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user?name={name}", "Marcovski"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_patchMethod_updated_success() throws Exception {
        User user = new User(8000, "Marco", "senhafacil", "marco@email.com",
                new Address("Teste", "teste", "teste", 10));

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(8000)).thenReturn(Optional.of(user));

        ObjectMapper objectMapper = new ObjectMapper();
        user.setName("MarcoSilva");
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch("/user?id=8000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MarcoSilva"))
                .andExpect(jsonPath("$.password").value("senhafacil"));
    }

    @Test
    void testUpdateUser_patchMethod_tryToUpdateANotFoundUser_notUpdated_failed() throws Exception {
        when(userRepository.getReferenceById(eq(7000))).thenReturn(null);

        mockMvc.perform(patch("/user?id=7000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_patchMethod_notFound_notUpdated_failed() throws Exception {
        when(userRepository.getReferenceById(7000)).thenReturn(null);

        mockMvc.perform(patch("/user?id=7000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_patchMethod_emptyUserName_notUpdated_failed() throws Exception {
        User user = new User(8000, "Marco", "Teste", "marco@email.com",
                new Address("Teste", "teste", "teste", 10));
        when(userRepository.save(any(User.class))).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        user.setName("");
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch("/user?id=8000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_patchMethod_emptyPassword_notUpdated_failed() throws Exception {
        User user = new User(8000, "Marco", "Teste", "marco@email.com",
                new Address("Teste", "teste", "teste", 10));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.getReferenceById(8000)).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();
        user.setPassword("");
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch("/user?id=8000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_putMethod_updated_success() throws Exception {
        User user = new User(8000, "Marco", "Teste", "marco@email.com",
                new Address("Teste", "teste", "teste", 10));

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(8000)).thenReturn(Optional.of(user));

        ObjectMapper objectMapper = new ObjectMapper();
        user.setName("MarcoSilva1198");
        user.setPassword("");
        String objUser = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/user?id=8000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MarcoSilva1198"))
                .andExpect(jsonPath("$.password").value(""));
    }

    @Test
    void testUpdateUser_putMethod_notFound_failed() throws Exception {

        when(userRepository.getReferenceById(2)).thenReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String userObject = objectMapper.writeValueAsString(new User());

        mockMvc.perform(put("/user?id={id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllUsers_success() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk());
    }
}
