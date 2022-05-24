package com.example.backend.service;

import com.example.backend.exception.UserError;
import com.example.backend.exception.UserException;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.repo.RoleRepo;
import com.example.backend.repo.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        log.error("User found in the database: {}", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public User saveUser(User user) {
        checkUsernameAvailability(user);
        checkEmailAvailability(user);

        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    private void checkEmailAvailability(User user) {
        userRepo.findByEmail(user.getEmail())
                .ifPresent(x -> {
                    throw new UserException(UserError.USER_EMAIL_NOT_AVAILABLE);
                });
    }

    private void checkUsernameAvailability(User user) {
        userRepo.findByUsername(user.getUsername())
                .ifPresent(x -> {
                    throw new UserException(UserError.USER_LOGIN_NOT_AVAILABLE);
                });
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Saving role {} to user {}", roleName, username);

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
        Role role = roleRepo.findByName(roleName);

        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    @Override
    public UsersUtils getUsers() {
        log.info("Fetching all users");
        UsersUtils users = new UsersUtils();
        users.setUsers(userRepo.findAll());
        return users;
    }

    @Data
    public static class UsersUtils {
        List<User> users = new ArrayList<>();
    }

}
