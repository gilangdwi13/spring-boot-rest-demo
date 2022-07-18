package com.gilang.demo.controller;

import com.gilang.demo.entity.User;
import com.gilang.demo.payload.LoginRequest;
import com.gilang.demo.response.LoginResponse;
import com.gilang.demo.response.Response;
import com.gilang.demo.security.JWTTokenProvider;
import com.gilang.demo.security.UserToken;
import com.gilang.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth")
public class AuthContoller {

    @Autowired
    UserService userService;

    @Autowired
    Environment env;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest) {
        User currentUser = userService.findUserAuth(loginRequest.getUsername());
        request.setAttribute("employee_id", currentUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(authentication);
        String refreshToken = (String)jwtTokenProvider.generateRefreshToken(authentication).get("refreshToken");
        Date expiredRefreshToken = (Date)jwtTokenProvider.generateRefreshToken(authentication).get("expiryDate");

        return ResponseEntity.ok(new Response(new LoginResponse(
                currentUser,
                new UserToken(accessToken, refreshToken, expiredRefreshToken)
        )));
    }
}
