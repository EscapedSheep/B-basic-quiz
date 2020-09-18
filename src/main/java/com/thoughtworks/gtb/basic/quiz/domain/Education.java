package com.thoughtworks.gtb.basic.quiz.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.gtb.basic.quiz.serializer.UserSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;

import static com.thoughtworks.gtb.basic.quiz.domain.PropertyVariable.*;
import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Education {
    @Id
    @GeneratedValue
    private long educationId;

    @ManyToOne
    @JsonSerialize(using = UserSerializer.class)
    @JsonProperty("userId")
    private User user;

    @NotNull(message = YEAR_INVALID)
    private long year;

    @NotBlank(message = TITLE_INVALID)
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = TITLE_INVALID)
    private String title;

    @NotBlank(message = DESC_INVALID)
    @Size(min = EDU_DESC_MIN_LENGTH, max = EDU_DESC_MAX_LENGTH, message = DESC_INVALID)
    private String description;

}
