package com.example.tu_campaign_management_tool_api.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    @Getter
    private String username;

    @NotNull
    @Getter
    private String password;

}
