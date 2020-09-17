package com.thoughtworks.gtb.basic.quiz.repository;

import com.thoughtworks.gtb.basic.quiz.domain.Education;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EducationRepository {
    private List<Education> educations;

    @PostConstruct
    private void init() {
        educations = new ArrayList<>();
        Education educationFirst = Education.builder()
                .description("Aspernatur, mollitia, quos maxime eius suscipit sed beatae ducimus quaerat quibusdam perferendis? Iusto, quibusdam asperiores unde repellat.")
                .title("First level graduation in Graphic Design")
                .year(2009)
                .userId(1)
                .build();

        Education educationSecondary = Education.builder()
                .description("Eos, explicabo, nam, tenetur et ab eius deserunt aspernatur ipsum ducimus quibusdam quis voluptatibus.")
                .title("Secondary school specializing in artistic")
                .year(2005)
                .userId(1)
                .build();
        addEducation(educationSecondary);
        addEducation(educationFirst);
    }

    public Education addEducation(Education education) {
        educations.add(education);
        return education;
    }

    public List<Education> getEducations(long userId) {
        return educations.stream().filter(education -> education.getUserId() == userId).collect(Collectors.toList());
    }

    // GTB: - 未删除的无用代码
    public List<Education> getEducations() {
        return new ArrayList<>(educations);
    }

    // GTB: - 不要专门为了 testing 而增加方法
    public void setEducations(List<Education> educations) {
        this.educations = new ArrayList<>(educations);
    }
}
