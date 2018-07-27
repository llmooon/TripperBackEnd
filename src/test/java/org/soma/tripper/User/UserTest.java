package org.soma.tripper.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.domain.User;
import org.soma.tripper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Before
    public void makeuser(){
        userRepository.save(User.builder()
                .email("aaaa@aaaa.com")
                .password("aaaa")
                .name("a")
                .sex(1)
                .build());
    }
    @After
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void getUser(){
        //when
        List<User> userList = userRepository.findAll();
        //then
        User user = userList.get(0);
        assertThat(user.getEmail(),is("leeth"));
    }

    @Test
    public void loginUser(){
        //User user = userRepository.findUserByEmailAndPassword("leeth","mylove");
        //assertThat(user.getEmail(),is("leeth"));
    }
}
