package com.example.tu_campaign_management_tool_api.jwtUtils;

import com.example.tu_campaign_management_tool_api.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestJwtUtils {


    @Mock
    private Authentication authentication;

    private JwtUtilsMock jwtUtils;

    private String jwtSecret = "ZxGy+zQ7DImWbxguAPFFku47wRtzRnHy29bFFCKdKPg=";
    private UserDetailsImpl userDetailsImpl;
    private String jwtToken;
    private boolean expectedBoolean;
    private boolean actualBoolean;

    private void arrangeJwtUtilsMock() {
        this.jwtUtils = new JwtUtilsMock(
                this.jwtSecret, 3600000);
    }

    private void arrangeUserDetailsImpl() {
        this.userDetailsImpl = new UserDetailsImpl(
                "1", "Robin",
                "test123", Arrays.asList(() -> "Super User e-Sales"));
    }

    private void arrangeExpectedBoolean(boolean paramBoolean) {
        this.expectedBoolean = paramBoolean;
    }

    private void actActualBoolean(String paramJwtToken) {
        this.actualBoolean = this.jwtUtils.validateJwtToken(paramJwtToken);
    }

    private void arrangeJwtToken(String paramJwtToken) {
        this.jwtToken = paramJwtToken;
    }

    private void actGenerateToken() {
        when(this.authentication.getPrincipal()).thenReturn(this.userDetailsImpl);
        this.jwtToken = this.jwtUtils.generateJwtToken(this.authentication);
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

    @Test
    public void should_return_true_when_jwtToken_is_valid() {
        // Arrange
        arrangeExpectedBoolean(true);
        long expirationMillis = 3600000; // Token will expire after 1 hour
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);
        String jwtToken = Jwts.builder()
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                .compact();
        arrangeJwtUtilsMock();
        // Act
        actActualBoolean(jwtToken);
        // Assert
        assertThat(this.actualBoolean, is(this.expectedBoolean));
    }

    @Test
    public void should_return_false_when_jwtToken_is_invalid() {
        // Arrange
        arrangeJwtToken("aaa");
        arrangeExpectedBoolean(false);
        arrangeJwtUtilsMock();
        // Act
        actActualBoolean(this.jwtToken);
        // Assert
        assertThat(this.actualBoolean, is(this.expectedBoolean));
    }

    @Test
    public void should_return_false_when_jwtToken_is_expired() {
        // Generate an expired JWT token
        arrangeExpectedBoolean(false);
        Date expirationDate = new Date(System.currentTimeMillis() - 1000);
        arrangeJwtUtilsMock();
        String expiredToken = Jwts.builder()
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, this.jwtSecret)
                .compact();
        actActualBoolean(expiredToken);
        assertThat(this.actualBoolean, is(this.expectedBoolean));
    }
    
    @Test
    public void should_return_false_when_jwtToken_is_empty() {
        arrangeExpectedBoolean(false);
        arrangeJwtUtilsMock();
        String emptyToken = "";
        actActualBoolean(emptyToken);
        assertThat(this.actualBoolean, is(this.expectedBoolean));
    }

    @Test
    public void should_return_false_when_format_of_jwtToken_is_unsupported() {
        arrangeExpectedBoolean(false);
        arrangeJwtUtilsMock();
        String emptyToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMSJ9.XXXX";
        actActualBoolean(emptyToken);
        assertThat(this.actualBoolean, is(this.expectedBoolean));
    }
}
