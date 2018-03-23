package com.jmb;

import com.jmb.domain.User;
import com.jmb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener
    public void seedData(ContextRefreshedEvent event) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        userService.addUser(user);
    }
}
