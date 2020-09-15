package com.thoughtworks.gtb.basic.quiz.repository;

import com.thoughtworks.gtb.basic.quiz.domain.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private List<User> users;

    private AtomicInteger idGenerator;

    @PostConstruct
    private void init() {
        users = new ArrayList<>();
        idGenerator = new AtomicInteger(1);
    }
}
