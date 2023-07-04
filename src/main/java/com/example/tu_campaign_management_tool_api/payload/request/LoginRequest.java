package com.example.tu_campaign_management_tool_api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginRequest {
    @NotBlank
    @Getter
    private String username;

    @NotBlank
    @Getter
    private String password;

}
