package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.jwtUtils.JwtUtilsMock;
import com.example.tu_campaign_management_tool_api.payload.request.LoginRequest;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class TestAuthController {

    @InjectMocks
    public AuthController authController;

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsImpl userDetails;
    @Mock
    public JwtUtilsMock jwtUtils;

    @Test
    public void should_return_a_statusCode200_containing_an_object_with_token_and_userDetails_when_authentication_went_successful() {
        // Arrange
        int expectedStatusCode = 200;
        LoginRequest loginRequest = new LoginRequest("Robin", "12345678");
        // Act
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).
                thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn("T14832");
        when(userDetails.getUsername()).thenReturn("Robin");
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("Super User e-Sales"));
        when(userDetails.getAuthorities()).thenAnswer(invocation -> authorities);
        int actualStatusCode = this.authController.authenticateUser(loginRequest).getStatusCode().value();
        // Assert
        assertThat(actualStatusCode, is(expectedStatusCode));
    }

    @Test
    public void should_return_an_errorMessage_when_authentication_failed() {
        // Arrange
        String expectedErrorMessage = "Unauthorized error: Bad credentials";
        LoginRequest loginRequest = new LoginRequest("Robin", "12345679");
        this.authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        // Act
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Unauthorized error: Bad credentials"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                this.authController.authenticateUser(loginRequest));
        assertThat(exception.getMessage(), is(expectedErrorMessage));
    }

    @Test
    public void should_return_statusCodeOf400_when_loginRequest_is_invalid() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        String requestJson = "{\"login\":\"robin\", \"password\":\"test123\"}";

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
