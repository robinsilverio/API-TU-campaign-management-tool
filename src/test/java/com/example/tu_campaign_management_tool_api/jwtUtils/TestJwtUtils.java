package com.example.tu_campaign_management_tool_api.jwtUtils;

import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestJwtUtils {


    @Mock
    private Authentication authentication;

    private JwtUtilsMock jwtUtils;

    private String jwtSecret;
    private int jwtExpirationMs; // 1 hour
    private UserDetailsImpl userDetailsImpl;
    private String jwtToken;

    public void arrangeJwtUtilsMock() {
        this.jwtUtils = new JwtUtilsMock(
                "ZxGy+zQ7DImWbxguAPFFku47wRtzRnHy29bFFCKdKPg=", 3600000);
    }

    public void arrangeUserDetailsImpl() {
        userDetailsImpl = new UserDetailsImpl(
                "1", "Robin",
                "test123", Arrays.asList(() -> "Super User e-Sales"));
    }

    private void actGenerateToken() {
        when(this.authentication.getPrincipal()).thenReturn(this.userDetailsImpl);
        jwtToken = this.jwtUtils.generateJwtToken(this.authentication);
    }

    @Test
    public void should_return_a_token_after_authentication() {
        // Arrange.
        arrangeJwtUtilsMock();
        arrangeUserDetailsImpl();

        // Act.
        actGenerateToken();

        // Assert.
        Assertions.assertNotNull(this.jwtToken);
    }

    @Test
    public void should_return_username_after_generating_token() {
        // Arrange
        String expectedUsername = "Robin";
        arrangeJwtUtilsMock();
        arrangeUserDetailsImpl();
        // Act.
        actGenerateToken();
        String tokenSubject = this.jwtUtils.getUserNameFromJwtToken(this.jwtToken);

        // Assert
        assertEquals(expectedUsername, tokenSubject);
    }
}
