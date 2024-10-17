package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.jwtUtils.JwtUtilsMock;
import com.example.tu_campaign_management_tool_api.payload.request.LoginRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.ErrorResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.RolesResponse;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
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

    private void mockAuthControllerMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

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
    public void should_return_role_of_eSales_when_jwtToken_is_valid() {
        // Arrange.
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUMTQ4MzIiLCJyb2xlcyI6WyJST0xFX1NVUEVSX1VTRVJfRS1TQUxFUyJdLCJpYXQiOjE3MjkxNTQwNzYsImV4cCI6MTcyOTI3NzUzM30.fCtZ-KEax90Vv6U478esk06jAGbVnGiPgcepedikMZY";
        String expectedRole = "ROLE_SUPER_USER_E-SALES";

        // Act.
        when(jwtUtils.validateJwtToken(jwtToken)).thenReturn(true);
        when(jwtUtils.getRolesFromJwtToken(jwtToken)).thenReturn(List.of("ROLE_SUPER_USER_E-SALES"));
        ResponseEntity<RolesResponse> response = (ResponseEntity<RolesResponse>) authController.validateJwtToken(jwtToken);

        // Assert.
        assertThat(response.getBody().getRoles().get(0), is(expectedRole));

    }

    @Test
    public void should_return_errorMessage_when_jwtToken_is_invalid() {
        // Arrange.
        String expectedErrorMessage = "Invalid Token";

        // Act.
        when(jwtUtils.validateJwtToken(null)).thenReturn(false);
        ResponseEntity<ErrorResponse> response = (ResponseEntity<ErrorResponse>) authController.validateJwtToken(null);

        // Arrange.
        assertThat(expectedErrorMessage, is(response.getBody().getErrorMessage()));
    }

    @Test
    public void should_return_statusCodeOf400_when_loginRequest_is_invalid() throws Exception {

        mockAuthControllerMvc();

        String requestJson = "{\"login\":\"robin\", \"password\":\"test123\"}";

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void should_return_statusCodeOf401_when_jwtToken_is_invalid() throws Exception {
        mockAuthControllerMvc();
        String requestParam = "";
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("paramToken", requestParam);

        // Act
        when(jwtUtils.validateJwtToken(requestParam)).thenReturn(false);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/validateJwt")
                .params(requestParams))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
