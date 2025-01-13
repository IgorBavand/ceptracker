package com.igorbavand.ceptracker.application.service;


import com.igorbavand.ceptracker.application.dto.request.AuthenticationRequest;
import com.igorbavand.ceptracker.application.dto.response.AuthenticationResponse;
import com.igorbavand.ceptracker.application.dto.response.JwtResponse;
import com.igorbavand.ceptracker.domain.model.Role;
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
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public ResponseEntity<?> getToken(AuthenticationRequest authenticationRequest) {
        try {
            // Autentica o usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            // Carrega os detalhes do usuário
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            // Verifica se o userDetails foi carregado corretamente
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }

            // Gera o token JWT
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Converte as authorities para uma lista de strings (ou roles específicas)
            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            // Cria o objeto de resposta
            AuthenticationResponse response = new AuthenticationResponse(
                    userDetails.getUsername(),
                    new JwtResponse(jwt),
                    roles
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Captura erros de autenticação
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        }
    }

}
