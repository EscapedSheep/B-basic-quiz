package com.thoughtworks.gtb.basic.quiz.service;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {
    private EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<Education> getEducation(long userId) {
        return educationRepository.getEducations(userId);
    }

    public Education addEducation(long userId, Education education) {
        education.setUserId(userId);
        return educationRepository.addEducation(education);
    }
}
