package com.thoughtworks.gtb.basic.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    private long userId;

    private long year;

    private String title;

    private String description;
}
