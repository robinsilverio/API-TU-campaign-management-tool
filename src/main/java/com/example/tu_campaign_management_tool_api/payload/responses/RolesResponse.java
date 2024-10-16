package com.example.tu_campaign_management_tool_api.payload.responses;

import lombok.Getter;

import java.util.List;

public class RolesResponse {
    @Getter
    private List<String> roles;
    public RolesResponse(List<String> paramRoles) { this.roles = paramRoles; }
}
