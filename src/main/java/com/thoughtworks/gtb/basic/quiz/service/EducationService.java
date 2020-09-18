package com.thoughtworks.gtb.basic.quiz.service;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.repository.EducationRepository;
import com.thoughtworks.gtb.basic.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {
    private EducationRepository educationRepository;

    private UserRepository userRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    public List<Education> getEducation(long userId) {
        User findUser = getUserIfExisted(userId);
        return educationRepository.findAllByUser(findUser);
    }

    public Education addEducation(long userId, Education education) {
        User findUser = getUserIfExisted(userId);
        education.setUser(findUser);
        return educationRepository.save(education);
    }

    private User getUserIfExisted(long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
