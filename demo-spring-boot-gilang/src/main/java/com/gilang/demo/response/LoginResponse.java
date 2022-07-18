package com.gilang.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gilang.demo.entity.User;
import com.gilang.demo.security.UserToken;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;


@Data
public class LoginResponse {

        private Long userId;
        private String username;
        private String name;
        private String role;
        private UserToken token;

        public LoginResponse(Long userId, String username, String name, String role, UserToken token) {
            this.userId = userId;
            this.username = username;
            this.name = name;
            this.role = role;
            this.token = token;
        }

        public LoginResponse(User users, UserToken token) {
            this.userId = users.getUserId();
            this.username = users.getUsername();
            this.name = users.getName();
            this.role = users.getRole();
            this.token = token;
        }
}

