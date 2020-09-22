package com.thoughtworks.gtb.basic.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.Error;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.service.EducationService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EducationController.class)
@AutoConfigureMockMvc
class EducationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EducationService educationService;

    private JacksonTester<Education> educationJacksonTester;

    private Education education;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        education = Education.builder()
                .description("testDesc")
                .educationId(1)
                .title("testTitle")
                .year(2019)
                .build();
    }

    @Nested
    class AddEducation {
        @Test
        void when_user_id_existed() throws Exception {
            when(educationService.addEducation(anyLong(), any(Education.class))).thenReturn(education);

            MockHttpServletResponse response = mockMvc.perform(post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(educationJacksonTester.write(Education.builder().description("testDesc").title("testTitle").year(2019).build()).getJson()))
                    .andReturn().getResponse();

            assertEquals(response.getContentAsString(), educationJacksonTester.write(education).getJson());
        }

        @Test
        void when_user_id_is_not_existed() throws Exception {
            when(educationService.addEducation(anyLong(), any(Education.class))).thenThrow(new UserNotFoundException());

            MockHttpServletResponse response = mockMvc.perform(post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(educationJacksonTester.write(Education.builder().description("testDesc").title("testTitle").year(2019).build()).getJson()))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        }
    }

    @Nested
    class getEducation {
        @Test
        void when_user_id_existed() throws Exception {
            List<Education> educationList = new ArrayList<>();
            educationList.add(education);
            when(educationService.getEducation(anyLong())).thenReturn(educationList);
            MockHttpServletResponse response = mockMvc.perform(get("/users/1/educations")).andReturn().getResponse();
            ObjectMapper objectMapper = new ObjectMapper();
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(educationList));
        }

        @Test
        void when_user_id_is_not_existed() throws Exception {
            when(educationService.getEducation(anyLong())).thenThrow(new UserNotFoundException());

            MockHttpServletResponse response = mockMvc.perform(get("/users/1/educations")).andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
        }
    }
}