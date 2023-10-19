package com.project.auth.security;

import com.project.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtGeneratorImplTest {

    @InjectMocks
    private JwtGeneratorImpl jwtGenerator;

    @Value("${jwt.secret}")
    private String secret;

    @Mock
    private SignatureAlgorithm signatureAlgorithm;

    @Mock
    private Claims claims;

    @Mock
    private Jws<Claims> jws;

    @Mock
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(jwtGenerator, "secret", "testSecret");
    }

    @Test
    public void testGenerateToken() {
        Mockito.when(user.getUsername()).thenReturn("testUser");
        Mockito.when(claims.setSubject("testUser")).thenReturn(claims);

        Mockito.when(Jwts.builder()).thenReturn(Jwts.builder());
        Mockito.when(Jwts.builder().setSubject("testUser")).thenReturn(Jwts.builder().setSubject("testUser"));
        Mockito.when(Jwts.builder().setSubject("testUser").setIssuedAt(Mockito.any())).thenReturn(Jwts.builder().setSubject("testUser").setIssuedAt(Mockito.any()));
        Mockito.when(Jwts.builder().setSubject("testUser").setIssuedAt(Mockito.any()).signWith(signatureAlgorithm, (byte[]) Mockito.any())).thenReturn(Jwts.builder().setSubject("testUser").setIssuedAt(Mockito.any()).signWith(signatureAlgorithm, (byte[]) Mockito.any()));
        Mockito.when(Jwts.builder().setSubject("testUser").setIssuedAt(Mockito.any()).signWith(signatureAlgorithm, (byte[]) Mockito.any()).compact()).thenReturn("testToken");

        String token = jwtGenerator.generateToken(user).get("token");

        assertEquals("testToken", token);
    }
}
