package com.thoughtworks.gtb.basic.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;

    private String name;

    private long age;

    private String avatar;

    private String description;
}
