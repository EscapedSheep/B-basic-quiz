package com.thoughtworks.gtb.basic.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.repository.EducationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.NAME_INVALID;
import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.TITLE_INVALID;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// GTB: - 有测试不通过
@SpringBootTest
@AutoConfigureMockMvc
class EducationControllerTest {
    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private MockMvc mockMvc;

    private Education education;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        educationRepository.setEducations(new ArrayList<>());
        education = Education.builder()
                .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
                .title("First level graduation in Graphic Design")
                .year(2009)
                .userId(1)
                .build();
    }

    @Test
    void should_get_education_list_given_user_id() throws Exception {
        educationRepository.addEducation(education);

        mockMvc.perform(get("/users/1/educations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year", is((int)education.getYear())))
                .andExpect(jsonPath("$[0].title", is(education.getTitle())))
                .andExpect(jsonPath("$[0].userId", is((int)education.getUserId())))
                .andExpect(jsonPath("$[0].description", is(education.getDescription())));
    }

    @Test
    void should_add_education_to_list_when_call_post_api_given_education_json() throws Exception {
        String json = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.year", is((int)education.getYear())))
                .andExpect(jsonPath("$.title", is(education.getTitle())))
                .andExpect(jsonPath("$.userId", is((int)education.getUserId())))
                .andExpect(jsonPath("$.description", is(education.getDescription())));
    }

    @Test
    void should_receive_error_when_call_post_api_given_invalid_education_info() throws Exception {
        education.setTitle(null);
        String json = objectMapper.writeValueAsString(education);

        mockMvc.perform(post("/users/1/educations").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(TITLE_INVALID)))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())));
    }
}