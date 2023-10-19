package com.project.auth.security;

import com.project.auth.entity.User;

import java.util.Map;
public interface JwtGeneratorInterface {
Map<String, String> generateToken(User user);
}