package com.project.auth.controller;

import com.project.auth.entity.User;
import com.project.auth.security.JwtGeneratorInterface;
import com.project.auth.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private JwtGeneratorInterface jwtGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostUser() {
        User user = new User("1", "testUser", "password", "USER");
        Mockito.doNothing().when(userService).saveUser(user);

        ResponseEntity<?> response = userController.postUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testLoginUser() {
        User user = new User("1", "testUser", "password", "USER");
        Map<String, String> token = new HashMap<>();
        token.put("testToken", "123");

        Mockito.when(userService.getUserByNameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
        Mockito.when(jwtGenerator.generateToken(user)).thenReturn(token);
        ResponseEntity<?> response = userController.loginUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLoginUserWithEmptyCredentials() {
        User user = new User();
        ResponseEntity<?> response = userController.loginUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testLoginUserWithInvalidCredentials() {
        User user = new User("1", "testUser", "password", "USER");
        Mockito.when(userService.getUserByNameAndPassword(user.getUsername(), user.getPassword())).thenReturn(null);

        ResponseEntity<?> response = userController.loginUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}