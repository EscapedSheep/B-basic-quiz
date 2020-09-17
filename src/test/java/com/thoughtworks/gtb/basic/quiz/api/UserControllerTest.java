package com.thoughtworks.gtb.basic.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.NAME_INVALID;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// GTB: + 有测试，覆盖基本场景
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        userRepository.setUsers(new ArrayList<>());
        user = User.builder()
                .name("KAMIL")
                .age(24)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build();
    }

    @Test
    void should_get_user_given_user_id() throws Exception {
        user = userRepository.addUser(user);

        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.age", is((int)(user.getAge()))))
                .andExpect(jsonPath("$.description", is(user.getDescription())))
                .andExpect(jsonPath("$.avatar", is(user.getAvatar())))
                .andExpect(jsonPath("$.id", is((int)(user.getId()))));
    }

    @Test
    void should_add_user_to_list_given_user_json() throws Exception {
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.age", is((int)user.getAge())))
                .andExpect(jsonPath("$.avatar", is(user.getAvatar())))
                .andExpect(jsonPath("$.description", is(user.getDescription())))
                .andExpect(status().isCreated());

        assertEquals(userRepository.getUsers().size(), 1);
        User findUser = userRepository.getUsers().stream().findFirst().get();
        assertEquals(findUser.getName(), user.getName());
        assertEquals(findUser.getAge(), user.getAge());
        assertEquals(findUser.getAvatar(), user.getAvatar());
        assertEquals(findUser.getDescription(), user.getDescription());
    }

    @Test
    void should_receive_error_when_call_post_api_given_invalid_user() throws Exception {
        user.setName(null);
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(jsonPath("$.message", is(NAME_INVALID)))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(status().isBadRequest());
    }
}