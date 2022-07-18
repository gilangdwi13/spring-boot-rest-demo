package com.gilang.demo.service;

import com.gilang.demo.entity.User;
import com.gilang.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.ws.rs.ForbiddenException;

@Service
@Configuration
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserAuth(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ForbiddenException("Invalid Username")
        );
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ForbiddenException("Invalid User Id")
        );
    }
}
