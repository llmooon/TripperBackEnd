package org.soma.tripper.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soma.tripper.user.dto.UserDTO;
import org.soma.tripper.user.repository.UserRepository;
import org.soma.tripper.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    UserDTO userDTO1;
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Before
    public void makeuser(){
        userDTO1 = UserDTO.builder()
                .email("llmooon@naver.com")
                .password("1234")
                .sex(1)
                .name("아")
                .device_token("4444")
                .build();
    }

    @After
    public void cleanup(){
        userRepository.deleteAll();
    }

    @Test
    public void saveAndGetUser(){
        userService.registerUser(userDTO1);
        //more...
    }
}
