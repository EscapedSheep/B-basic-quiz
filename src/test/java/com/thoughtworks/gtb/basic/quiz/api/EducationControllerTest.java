package com.thoughtworks.gtb.basic.quiz.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.service.EducationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EducationController.class)
@AutoConfigureJsonTesters
class EducationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EducationService educationService;

    @Autowired
    private JacksonTester<Education> educationJacksonTester;

    private Education education;

    @BeforeEach
    void setUp() {
        Mockito.reset(educationService);
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
        void when_user_id_existed_should_add_education() throws Exception {
            when(educationService.addEducation(anyLong(), any(Education.class))).thenReturn(education);

            Education validEducation = Education.builder().description("testDesc").title("testTitle").year(2019).build();

            MockHttpServletResponse response = mockMvc.perform(post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(educationJacksonTester.write(validEducation).getJson()))
                    .andReturn().getResponse();

            assertEquals(response.getContentAsString(), educationJacksonTester.write(education).getJson());
            verify(educationService).addEducation(1, validEducation);
        }

        @Test
        void when_user_id_is_not_existed_should_return_not_found() throws Exception {
            when(educationService.addEducation(anyLong(), any(Education.class))).thenThrow(new UserNotFoundException());

            Education validEducation = Education.builder().description("testDesc").title("testTitle").year(2019).build();

            MockHttpServletResponse response = mockMvc.perform(post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(educationJacksonTester.write(validEducation).getJson()))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
            verify(educationService).addEducation(1, validEducation);
        }

        @Test
        void when_education_info_invalid_should_return_bad_request() throws Exception{
            Education invalidEdu = Education.builder().build();

            MockHttpServletResponse response = mockMvc.perform(post("/users/1/educations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(educationJacksonTester.write(invalidEdu).getJson()))
                    .andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class getEducation {
        @Test
        void when_user_id_existed_should_return_education_list() throws Exception {
            List<Education> educationList = new ArrayList<>();
            educationList.add(education);

            when(educationService.getEducation(anyLong())).thenReturn(educationList);
            MockHttpServletResponse response = mockMvc.perform(get("/users/1/educations")).andReturn().getResponse();

            ObjectMapper objectMapper = new ObjectMapper();
            assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(educationList));

            verify(educationService).getEducation(1);
        }

        @Test
        void when_user_id_is_not_existed_should_return_not_found() throws Exception {
            when(educationService.getEducation(anyLong())).thenThrow(new UserNotFoundException());

            MockHttpServletResponse response = mockMvc.perform(get("/users/1/educations")).andReturn().getResponse();

            assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
            verify(educationService).getEducation(1);
        }
    }
}