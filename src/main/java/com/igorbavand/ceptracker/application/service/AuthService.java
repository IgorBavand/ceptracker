package com.igorbavand.ceptracker.application.service;


import com.igorbavand.ceptracker.application.dto.request.AuthenticationRequest;
import com.igorbavand.ceptracker.application.dto.response.AuthenticationResponse;
import com.igorbavand.ceptracker.application.dto.response.JwtResponse;
import com.igorbavand.ceptracker.infrastructure.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @SuppressWarnings("checkstyle:methodlength")
    public ResponseEntity<?> getToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }

            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            AuthenticationResponse response = new AuthenticationResponse(
                    userDetails.getUsername(),
                    new JwtResponse(jwt),
                    roles
            );

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + ex.getMessage());
        }
    }

}
