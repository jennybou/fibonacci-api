package com.jmb.services;

import com.jmb.domain.User;
import com.jmb.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("pwd");

        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(testUser);
    }

    @Test
    public void addUniqueUser() {
        User testUser = new User();
        testUser.setUsername("newuser");
        testUser.setPassword("pwd");
        userService.addUser(testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addingDuplicateUsernameCausesException() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("pwd");
        userService.addUser(testUser);
    }

}
