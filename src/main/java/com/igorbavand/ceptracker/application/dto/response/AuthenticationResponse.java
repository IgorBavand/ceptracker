package com.igorbavand.ceptracker.application.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String username;
    private final JwtResponse jwtResponse;

    public AuthenticationResponse(String username, JwtResponse user) {
        this.username = username;
        this.jwtResponse = user;
    }
}