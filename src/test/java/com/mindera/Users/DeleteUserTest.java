package com.mindera.Users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.Users.controller.UserController;
import com.mindera.Users.domain.User;
import com.mindera.Users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class DeleteUserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User Example");
        user.setPassword("Password Example");

        when(userService.create(any(User.class))).thenReturn(user);

        mockMvc.perform(delete("/user")
                        .param("id", String.valueOf(user.getId())))
                .andExpect(status().isOk());
    }
}