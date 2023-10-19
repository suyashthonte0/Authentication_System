package com.project.auth.service;

import com.project.auth.entity.User;
import com.project.auth.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
@Service
public interface UserService {
    public void saveUser(User user);
    public User getUserByNameAndPassword(String name, String password) throws UserNotFoundException;
}
