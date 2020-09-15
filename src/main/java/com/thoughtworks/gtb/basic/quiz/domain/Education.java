package com.thoughtworks.gtb.basic.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.thoughtworks.gtb.basic.quiz.domain.PropertyVariable.*;
import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    private long userId;

    @NotNull(message = YEAR_INVALID)
    private long year;

    @NotBlank(message = TITLE_INVALID)
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = TITLE_INVALID)
    private String title;

    @NotBlank(message = DESC_INVALID)
    @Size(min = EDU_DESC_MIN_LENGTH, max = EDU_DESC_MAX_LENGTH, message = DESC_INVALID)
    private String description;
}
