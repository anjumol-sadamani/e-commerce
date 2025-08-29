package com.example.demo.security;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    private final String jwtSecret;
    private final long jwtExpiration;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret,
                   @Value("${jwt.expiration}") long jwtExpiration ){
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String createJwtToken(User user){
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("role",user.getUserRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getKey())
                .compact();
    }

    public Map<String, Object> validateJwt(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userName = claims.getSubject();
            String roleStr = claims.get("role", String.class);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName", userName);
            userInfo.put("role", Role.valueOf(roleStr));
            return userInfo;

        } catch (Exception e) {
            throw new UnauthorizedException(List.of("Invalid token"));
        }
    }
}
