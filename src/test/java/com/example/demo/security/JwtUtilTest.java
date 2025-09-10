package com.example.demo.security;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp(){
        jwtUtil = new JwtUtil("mySecretKeyThatIsAtLeast32CharactersLong", 864000L);
    }

    @Test
    void createToken() {
        User user = new User(
                1L, "testUser", "password", "test@email.com", Role.USER, new ArrayList<>(), new UserProfile());
        String jwtToken = jwtUtil.createJwtToken(user);
        assertNotNull(jwtToken);
        assertFalse(jwtToken.isEmpty());
        assertTrue(jwtToken.contains("."));
    }

    @Test
    void validateJwt_validToken(){
        User user = new User(
                1L, "testUser", "password", "test@email.com", Role.USER,new ArrayList<>(), new UserProfile());
        String jwtToken = jwtUtil.createJwtToken(user);
        Map<String, Object> userInfo = jwtUtil.validateJwt(jwtToken);

        assertEquals("testUser",userInfo.get("userName"));
        assertEquals(Role.USER, userInfo.get("role"));
    }

    @Test
    void validateJwt_inValidToken(){
        assertThrows(UnauthorizedException.class,()->jwtUtil.validateJwt("fakeToken"));
    }
}
