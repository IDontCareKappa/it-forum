package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.security.Role;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();

}
