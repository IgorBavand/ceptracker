package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.application.dto.request.AuthenticationRequest;
import com.igorbavand.ceptracker.application.dto.response.AuthenticationResponse;
import com.igorbavand.ceptracker.infrastructure.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getToken_validCredentials_returnsJwtResponse() {
        AuthenticationRequest request = new AuthenticationRequest("user", "password");
        UserDetails userDetails = mock(UserDetails.class); // Mock do UserDetails
        String generatedJwt = "mocked-jwt";

        when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(userDetails);
        when(jwtUtil.generateToken(request.getUsername())).thenReturn(generatedJwt);
        when(userDetails.getUsername()).thenReturn(request.getUsername());

        ResponseEntity<?> responseEntity = authService.getToken(request);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        AuthenticationResponse responseBody = (AuthenticationResponse) responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("user", responseBody.getUsername());
        assertEquals(generatedJwt, responseBody.getJwtResponse().getAccessToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(request.getUsername());
        verify(jwtUtil, times(1)).generateToken(request.getUsername());
    }

    @Test
    void getToken_invalidCredentials_returnsUnauthorized() {
        AuthenticationRequest request = new AuthenticationRequest("user", "wrongpassword");

        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseEntity<?> responseEntity = authService.getToken(request);

        assertNotNull(responseEntity);
        assertEquals(401, responseEntity.getStatusCodeValue());
        assertEquals("Authentication failed: Bad credentials", responseEntity.getBody());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userDetailsService, jwtUtil);
    }

    @Test
    void getToken_userDetailsNotFound_returnsUnauthorized() {
        AuthenticationRequest request = new AuthenticationRequest("unknownUser", "password");

        when(userDetailsService.loadUserByUsername(request.getUsername())).thenReturn(null);

        ResponseEntity<?> responseEntity = authService.getToken(request);

        assertNotNull(responseEntity);
        assertEquals(401, responseEntity.getStatusCodeValue());
        assertEquals("Invalid username or password", responseEntity.getBody());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(request.getUsername());
        verifyNoInteractions(jwtUtil);
    }
}
