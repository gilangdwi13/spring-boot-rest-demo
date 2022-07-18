package com.gilang.demo.security;

import com.gilang.demo.entity.User;
import com.gilang.demo.exception.InvalidTokenException;
import com.gilang.demo.repository.UserRepository;
import io.jsonwebtoken.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    @Autowired
    UserRepository userRepository;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String generateToken(Authentication authentication) {

        org.springframework.security.core.userdetails.User ldapUserDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Date expiryDate = this.getExpiryDateAccessToken();
        Map<String, Object> claims = new HashMap<String, Object>(){{
            put("sub", ldapUserDetails.getUsername());
        }};
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Map<String, Object> generateRefreshToken(Authentication authentication) {
        Map<String, Object> result =new HashMap<String, Object>();
        org.springframework.security.core.userdetails.User ldapUserDetails = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        Date expiryDate = this.getExpiryDateRefreshToken();
        result.put("expiryDate", expiryDate);
        result.put("refreshToken", Jwts.builder()
                .setSubject(RefreshTokenClaim.SUBJECT_REFRESH_TOKEN)
                .claim("scope", new RefreshTokenClaim(ldapUserDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact());
        return result;
    }

    /*
     * Get user id from claim["scope"]
     * Use user id to generate token
     * */
    public UserToken regenerateUserToken(String refreshToken) {
        if(getClaimsFromJWT(refreshToken).get("scope") == null)
            throw new InvalidTokenException();

        String userTokenSubject = new ObjectMapper().convertValue(
                getClaimsFromJWT(refreshToken).get("scope"), RefreshTokenClaim.class)
                .getUser();

        Date expiryDateAccessToken = this.getExpiryDateAccessToken();
        Date expiryDateRefreshToken = this.getExpiryDateRefreshToken();

        String newAccessToken = Jwts.builder()
                .setSubject(userTokenSubject)
                .setIssuedAt(new Date())
                .setExpiration(expiryDateAccessToken)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        String newRefreshToken = Jwts.builder()
                .setSubject(RefreshTokenClaim.SUBJECT_REFRESH_TOKEN)
                .claim("scope", new RefreshTokenClaim(userTokenSubject))
                .setIssuedAt(new Date())
                .setExpiration(expiryDateRefreshToken)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        User user = userRepository.findByUsername(userTokenSubject).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + userTokenSubject)
        );

        return new UserToken(newAccessToken, newRefreshToken, expiryDateRefreshToken);
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /*public Boolean getIsLoginMobileFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (Boolean) claims.get("is_login_mobile");
    }*/

    public Claims getClaimsFromJWT(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public ValidateToken validateToken(String authToken) throws IllegalArgumentException, UnsupportedJwtException, SignatureException, ExpiredJwtException, MalformedJwtException{
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return new ValidateToken(true, null);
    }

    private Date getExpiryDateAccessToken() {
        return new Date(new Date().getTime() + 13000000);
    }

    private Date getExpiryDateRefreshToken() {
        return new Date(new Date().getTime() + 23000000);
    }
}
