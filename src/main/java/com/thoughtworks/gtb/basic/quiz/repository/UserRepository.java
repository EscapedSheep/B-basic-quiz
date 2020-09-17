package com.thoughtworks.gtb.basic.quiz.repository;

import com.thoughtworks.gtb.basic.quiz.domain.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private List<User> users;

    private AtomicInteger idGenerator;

    @PostConstruct
    private void init() {
        users = new ArrayList<>();
        idGenerator = new AtomicInteger(1);
        User initUser = User.builder()
                .name("KAMIL")
                .age(24)
                .avatar("https://inews.gtimg.com/newsapp_match/0/3581582328/0")
                .description("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Repellendus, non, dolorem, cumque distinctio magni quam expedita velit laborum sunt amet facere tempora ut fuga aliquam ad asperiores voluptatem dolorum! Quasi.")
                .build();
        addUser(initUser);
    }

    public User addUser(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    // GTB: - 不要专门为了 testing 而增加方法
    public void setUsers(List<User> users) {
        this.users = new ArrayList<>(users);
    }

    public Optional<User> getUser(long id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }
}
