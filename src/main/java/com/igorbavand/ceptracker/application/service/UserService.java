package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.domain.model.Role;
import com.igorbavand.ceptracker.domain.model.User;
import com.igorbavand.ceptracker.enums.RoleName;
import com.igorbavand.ceptracker.exception.BadRequestException;
import com.igorbavand.ceptracker.exception.NotFoundException;
import com.igorbavand.ceptracker.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @SuppressWarnings("checkstyle:methodlength")
    @Transactional
    public User saveUser(User user) {
        validateUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName(RoleName.ROLE_USER);
        if (userRole == null) {
            userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);
            roleService.saveRole(userRole);
        }

        Set<Role> savedRoles = new HashSet<>();
        savedRoles.add(userRole);

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            savedRoles.addAll(
                    user.getRoles()
                            .stream()
                            .map(role -> {
                                Role savedRole = roleService.findByName(role.getName());
                                return (savedRole != null) ? savedRole : roleService.saveRole(role);
                            })
                            .collect(Collectors.toSet())
            );
        }

        user.setRoles(savedRoles);

        return userRepository.save(user);
    }

    private void validateExistentUser(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Username already exists");
        }
    }

    private void validateUser(User user) {
        validateExistentUser(user.getUsername());

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new BadRequestException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be null or empty");
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

    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return findByUsername(username);
        } else {
            throw new NotFoundException("User not found with username: " + principal);
        }
    }
}
