package com.thoughtworks.gtb.basic.quiz.service;

import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
        userService = new UserService(userRepository);
        user = User.builder()
                .name("testName")
                .description("testDesc")
                .avatar("")
                .age(18)
                .id(1)
                .build();
    }

    @Nested
    class GetUser {
        @Test
        void when_user_existed_should_return_user() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

            User findUser = userService.getUser(1);

            assertEquals(findUser, user);
            verify(userRepository).findById(1l);
        }

        @Test
        void when_user_not_existed_should_throw_error() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.getUser(1));

            assertEquals(userNotFoundException.getMessage(), USER_NOT_FOUND);
            verify(userRepository).findById(1l);
        }
    }

    @Nested
    class AddUser {
        @Test
        void should_add_user() {
            when(userRepository.save(any(User.class))).thenReturn(user);
            User savedUser = userService.addUser(user);

            assertEquals(savedUser, user);
            verify(userRepository).save(user);
        }
    }
}