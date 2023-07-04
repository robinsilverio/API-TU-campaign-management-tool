package com.example.tu_campaign_management_tool_api.jwtUtils;

import com.example.tu_campaign_management_tool_api.security.jwt.JwtUtils;

public class JwtUtilsMock extends JwtUtils {

    public JwtUtilsMock() {

    }
    public JwtUtilsMock(String paramSecret, int paramJwtExpirationInMs) {
        this.jwtSecret = paramSecret;
        this.jwtExpirationMs = paramJwtExpirationInMs;
    }
}
