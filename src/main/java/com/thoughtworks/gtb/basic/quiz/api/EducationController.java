package com.thoughtworks.gtb.basic.quiz.api;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class EducationController {
    private EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/{id}/educations")
    public List<Education> getEducations(@PathVariable("id") long userId) {
        return educationService.getEducation(userId);
    }
}
