package com.thoughtworks.gtb.basic.quiz.service;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import com.thoughtworks.gtb.basic.quiz.domain.User;
import com.thoughtworks.gtb.basic.quiz.exception.UserNotFoundException;
import com.thoughtworks.gtb.basic.quiz.repository.EducationRepository;
import com.thoughtworks.gtb.basic.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<User> findUser = userRepository.getUser(userId);
        if (findUser.isPresent()) {
            return educationRepository.getEducations(userId);
        }
        throw new UserNotFoundException();
    }

    public Education addEducation(long userId, Education education) {
        education.setUserId(userId);
        return educationRepository.addEducation(education);
    }
}
