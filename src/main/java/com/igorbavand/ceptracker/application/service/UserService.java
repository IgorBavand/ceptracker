package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.domain.model.Role;
import com.igorbavand.ceptracker.domain.model.User;
import com.igorbavand.ceptracker.enums.RoleName;
import com.igorbavand.ceptracker.exception.NotFoundException;
import com.igorbavand.ceptracker.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository cannot be null");
        this.roleService = Objects.requireNonNull(roleService, "RoleService cannot be null");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "PasswordEncoder cannot be null");
    }

    @Transactional
    public User saveUser(User user) {
        validateUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> savedRoles = new HashSet<>();

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            savedRoles.add(roleService.findByName(RoleName.ROLE_USER));
        }

        for (Role role : user.getRoles()) {
            Role savedRole = roleService.findByName(role.getName());
            if (savedRole == null) {
                savedRole = roleService.saveRole(role);
            }
            savedRoles.add(savedRole);
        }
        user.setRoles(savedRoles);

        return userRepository.save(user);
    }


    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }

    public User assignRoleToUser(String username, RoleName roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleService.findByName(roleName);

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
        return userRepository.save(user);
    }


    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }
}
