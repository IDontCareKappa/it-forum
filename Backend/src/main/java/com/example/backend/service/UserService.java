package com.example.backend.service;

import com.example.backend.model.Role;
import com.example.backend.model.User;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    UserServiceImpl.UsersUtils getUsers();

}
