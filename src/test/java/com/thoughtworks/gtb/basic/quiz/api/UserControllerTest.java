package com.thoughtworks.gtb.basic.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    private JacksonTester<User> userJacksonTester;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        user = User.builder()
                .name("testName")
                .description("testDesc")
                .avatar("")
                .age(18)
                .id(1)
                .build();
    }

    @Nested
    class getUser {
        @Test
        void when_user_existed() throws Exception {
            when(userService.getUser(anyLong())).thenReturn(user);

            MockHttpServletResponse response = mockMvc.perform(get("/users/1")).andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.OK.value());
            assertEquals(response.getContentAsString(), userJacksonTester.write(user).getJson());
        }

        @Test
        void when_user_not_existed() throws Exception {
            when(userService.getUser(anyLong())).thenThrow(new UserNotFoundException());

            MockHttpServletResponse response = mockMvc.perform(get("/users/1")).andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    class addUser {
        @Test
        void when_user_info_valid() throws Exception {
            User validUser = User.builder()
                    .age(18)
                    .name("twUser")
                    .avatar("12345678")
                    .build();

            when(userService.addUser(any(User.class))).thenReturn(user);

            MockHttpServletResponse response = mockMvc.perform(post("/users")
                    .content(userJacksonTester.write(validUser).getJson())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.CREATED.value());
            assertEquals(response.getContentAsString(), userJacksonTester.write(user).getJson());
        }

        @Test
        void when_user_info_invalid() throws Exception {
            User invalidUser = User.builder()
                    .age(1)
                    .name("tw")
                    .avatar("")
                    .build();

            MockHttpServletResponse response = mockMvc.perform(post("/users")
                    .content(userJacksonTester.write(invalidUser).getJson())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        }

    }
}