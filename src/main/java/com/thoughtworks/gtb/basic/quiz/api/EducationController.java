package com.thoughtworks.gtb.basic.quiz.api;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@BaseController
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

    @PostMapping("/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public Education addEducation(@PathVariable("id") long userId, @RequestBody @Valid Education education) {
        return educationService.addEducation(userId, education);
    }
}
