package com.igorbavand.ceptracker.application.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationResponse {
    private final String username;
    private final JwtResponse jwtResponse;
    private final List<String> roles;

    public AuthenticationResponse(String username, JwtResponse jwtResponse, List<String> roles) {
        this.username = username;
        this.jwtResponse = jwtResponse;
        this.roles = roles;
    }
}
