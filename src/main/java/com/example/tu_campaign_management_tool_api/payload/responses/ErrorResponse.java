package com.example.tu_campaign_management_tool_api.payload.responses;


import lombok.Getter;

public class ErrorResponse {
    @Getter
    private String errorMessage;
    public ErrorResponse(String paramErrorMessage) { this.errorMessage = paramErrorMessage; }
}
