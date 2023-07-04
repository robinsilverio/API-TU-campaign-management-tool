package com.example.tu_campaign_management_tool_api.payload.responses;

import lombok.Getter;

import java.util.List;

public class JwtResponse {
    @Getter
    private String token;
    @Getter
    private String type = "Bearer";
    @Getter
    private String id;
    @Getter
    private String username;
    @Getter
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String username, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
