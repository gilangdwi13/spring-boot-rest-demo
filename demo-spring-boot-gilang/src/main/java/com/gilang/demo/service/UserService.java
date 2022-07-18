package com.gilang.demo.service;

import com.gilang.demo.entity.User;

public interface UserService {
    User findUserAuth(String username);
    User findById(Long userId);
}
