package com.jmb.services;

import com.jmb.domain.User;
import com.jmb.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public void addUser( User user) {
        User existing = userRepository.findByUsername(user.getUsername());
        if (existing != null) {
            logger.error("Could not create user with name: "+user.getUsername()+", User already exists");
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
