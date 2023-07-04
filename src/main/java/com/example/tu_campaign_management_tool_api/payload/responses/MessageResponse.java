package com.example.tu_campaign_management_tool_api.payload.responses;

import lombok.Getter;

public class MessageResponse {

    @Getter
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
