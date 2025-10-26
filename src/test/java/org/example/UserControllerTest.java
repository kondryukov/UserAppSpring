package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.UserController;
import org.example.dto.CreateUserRequest;
import org.example.dto.UpdateUserRequest;
import org.example.dto.UserResponse;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // поднимет только веб-слой с этим контроллером
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    public UserService userService;

    @Test
    void createReturns201AndLocationAndBody() throws Exception {
        UserResponse resp = new UserResponse(
                1L, "name", "name@mail.ru", 123, new Date(), new Date()
        );
        when(userService.createUser(ArgumentMatchers.any())).thenReturn(resp);

        var request = new CreateUserRequest("name", "name@mail.ru", 123);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/users/1")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("name@mail.ru"));
    }


    @Test
    void createInvalidRequestReturns400() throws Exception {
        var request = new CreateUserRequest("name", "", 123);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(400));
    }

    @Test
    void getByIdReturns200() throws Exception {
        var response = new UserResponse(1L, "name", "name@mail.ru", 123, new Date(), new Date());
        when(userService.readUser(1L)).thenReturn(response);

        mockMvc.perform(get("/users/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"));
    }

    @Test
    void listReturns200AndArray() throws Exception {
        var user1 = new UserResponse(1L, "name", "name@mail.ru", 12, new Date(), new Date());
        var user2 = new UserResponse(2L, "name2", "name2@mail.ru", 123, new Date(), new Date());
        when(userService.getUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email").value("name@mail.ru"));
    }

    @Test
    void updateReturns200() throws Exception {
        var request = new UpdateUserRequest("name", "newmail@mail.ru", 12);
        var response = new UserResponse(1L, "name", "name@mail.ru", 12, new Date(), new Date());
        when(userService.updateUser(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(response);


        mockMvc.perform(put("/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"));
    }

    @Test
    void deleteReturns204() throws Exception {
        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().is(204));
    }
}