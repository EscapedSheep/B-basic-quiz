package com.thoughtworks.gtb.basic.quiz.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.thoughtworks.gtb.basic.quiz.domain.PropertyVariable.*;
import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = NAME_INVALID)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_INVALID)
    private String name;

    @NotNull(message = AGE_INVALID)
    @Min(value = MIN_AGE, message = AGE_INVALID)
    private long age;

    @NotBlank(message = AVATAR_INVALID)
    @Size(min = AVATAR_MIN_LENGTH, max = AVATAR_MAX_LENGTH, message = AVATAR_INVALID)
    private String avatar;

    @Size(min = USER_DESC_MIN_LENGTH, max = USER_DESC_MAX_LENGTH, message = DESC_INVALID)
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    @JsonIgnore
    private List<Education> educationList;
}
