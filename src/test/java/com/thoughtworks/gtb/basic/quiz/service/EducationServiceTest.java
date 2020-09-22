package com.thoughtworks.gtb.basic.quiz.service;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.repository.EducationRepository;
import com.thoughtworks.gtb.basic.quiz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest {
    @Mock
    private EducationRepository educationRepository;

    @Mock
    private UserRepository userRepository;

    private EducationService educationService;
    private Education education;
    private User user;

    @BeforeEach
    void setUp() {
        Mockito.reset(educationRepository, userRepository);
        educationService = new EducationService(educationRepository, userRepository);
        user = User.builder()
                .name("testName")
                .description("testDesc")
                .avatar("")
                .age(18)
                .id(1)
                .build();
        education = Education.builder()
                .year(2019)
                .title("testTitle")
                .description("testDesc")
                .educationId(1)
                .user(user)
                .build();
    }

    @Nested
    class GetEducation {
        @Test
        void should_return_education_when_user_existed() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            List<Education> educationList = new ArrayList<>();
            educationList.add(education);
            when(educationRepository.findAllByUser(any(User.class))).thenReturn(educationList);

            List<Education> findEducations = educationService.getEducation(1);

            assertArrayEquals(findEducations.toArray(), educationList.toArray());
            verify(userRepository).findById(1l);
            verify(educationRepository).findAllByUser(user);
        }

        @Test
        void should_return_error_when_user_not_existed() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () -> educationService.getEducation(1));

            assertEquals(userNotFoundException.getMessage(), USER_NOT_FOUND);
        }
    }

    @Nested
    class AddEducation {
        @Test
        void should_add_education_when_user_existed() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(educationRepository.save(any(Education.class))).thenReturn(education);

            Education savedEducation = educationService.addEducation(1, education);

            assertEquals(savedEducation, education);
            verify(userRepository).findById(1l);
            verify(educationRepository).save(education);
        }
    }
}