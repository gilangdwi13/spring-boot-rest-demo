package com.gilang.demo.security;

import com.gilang.demo.entity.User;
import com.gilang.demo.exception.ThrowCustomAuthenticationError;
import com.gilang.demo.service.UserService;
import com.gilang.demo.util.ErrorType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "usersessiontoken, Authorization, Access-Control-Allow-Headers, Access-Control-Allow-Origin, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Username, Workstation-Id, ETag");
            response.setHeader("Access-Control-Expose-Headers", "ETag");
            String jwt = getJwtFromRequest(request);
            logger.debug("remote-address: " + request.getRemoteAddr());

            if (jwt == null) {
                logger.info("Empty JWT token request");
            } else {
                ValidateToken validateToken = null;
                String userId = null;
                try {
                    validateToken = tokenProvider.validateToken(jwt);
                    userId = tokenProvider.getUserIdFromJWT(jwt);
                } catch (ExpiredJwtException e) {
                    ThrowCustomAuthenticationError.expiredAccessToken(response, request);
                    return;
                } catch (SignatureException e) {
                    ThrowCustomAuthenticationError.invalidAccessToken(response, request);
                    return;
                }

                if (StringUtils.hasText(jwt) && validateToken.getStatus()) {
                    if(!userId.equalsIgnoreCase(RefreshTokenClaim.SUBJECT_REFRESH_TOKEN)) {
                        User user = userService.findUserAuth(userId);

                        setSecurityAuthenticationContext(request, user);
                        logger.info("JWTAUTH DONE!");
                    } else {
                        logger.error("Invalid user access token!");
                    }
                } else {
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityAuthenticationContext(HttpServletRequest request, User user) {
        UserDetails userDetails = customUserDetailsService.loadUser(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}