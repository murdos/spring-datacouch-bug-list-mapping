package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(CouchbaseTestContainerExtension.class)
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUserThenTryToLoadIt() {
        User user = new User();
        user.setLogin("user.login");
        user.setAuthorities(List.of("authority1", "authority2"));
        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();

        // Throws exception: org.springframework.data.mapping.model.MappingInstantiationException:
        // Failed to instantiate java.util.List using constructor NO_CONSTRUCTOR with arguments
        userRepository.findOneByLogin(user.getLogin());

    }

}
