package com.igorbavand.ceptracker.api;

import com.igorbavand.ceptracker.application.dto.request.AuthenticationRequest;
import com.igorbavand.ceptracker.application.service.AuthService;
import com.igorbavand.ceptracker.application.service.UserService;
import com.igorbavand.ceptracker.domain.model.User;
import com.igorbavand.ceptracker.enums.RoleName;
import com.igorbavand.ceptracker.infrastructure.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          UserService userService,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.getToken(authenticationRequest);
    }

    @PostMapping("/assign-role")
    public ResponseEntity<?> assignRoleToUser(@RequestParam String username, @RequestParam RoleName roleName) {
        User user = userService.assignRoleToUser(username, roleName);
        return ResponseEntity.ok(user);
    }
}