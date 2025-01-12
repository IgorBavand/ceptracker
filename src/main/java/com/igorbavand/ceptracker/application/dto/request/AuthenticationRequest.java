package com.igorbavand.ceptracker.application.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
