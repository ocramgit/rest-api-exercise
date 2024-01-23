package com.mindera.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.Users.controller.UserController;
import com.mindera.Users.domain.User;
import com.mindera.Users.repository.UserRepository;
import com.mindera.Users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UpdateOneFieldTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void updateOneField() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User Example");
        user.setPassword("Password Example");

        ObjectMapper objectMapper = new ObjectMapper();
        String userObject = objectMapper.writeValueAsString(user);

        mockMvc.perform(patch("/user?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userObject))
                .andExpect(jsonPath("$.name").value("User Test"))
                .andExpect(jsonPath("password").value("Password Example"));
    }
}