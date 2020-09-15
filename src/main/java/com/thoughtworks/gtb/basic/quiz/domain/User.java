package com.thoughtworks.gtb.basic.quiz.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private long id;

    @NotBlank(message = NAME_INVALID)
    @Size(min = 1, max = 128, message = NAME_INVALID)
    private String name;

    @NotNull(message = AGE_INVALID)
    @Min(value = 17, message = AGE_INVALID)
    private long age;

    @NotBlank(message = AVATAR_INVALID)
    @Size(min = 8, max = 512, message = AVATAR_INVALID)
    private String avatar;

    @Size(min = 0, max = 1024, message = DESC_INVALID)
    private String description;
}
