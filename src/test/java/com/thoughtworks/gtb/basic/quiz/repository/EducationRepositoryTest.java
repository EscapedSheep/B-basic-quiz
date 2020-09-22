package com.thoughtworks.gtb.basic.quiz.repository;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EducationRepositoryTest {
    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void should_find_all_education_given_user() {
        User user = User.builder()
                .name("testName")
                .description("testDesc")
                .avatar("testAvatar")
                .age(18)
                .build();

        user = testEntityManager.persistAndFlush(user);

        Education education = Education.builder()
                .year(2019)
                .title("testTitle")
                .description("testDesc")
                .user(user)
                .build();

        education = testEntityManager.persistAndFlush(education);

        List<Education> findEducations = educationRepository.findAllByUser(user);

        assertEquals(findEducations.size(), 1);
        assertEquals(findEducations.get(0), education);
    }
}