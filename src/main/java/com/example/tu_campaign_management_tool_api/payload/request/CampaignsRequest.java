package com.example.tu_campaign_management_tool_api.payload.request;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class CampaignsRequest {
    @Getter
    private List<Campaign> campaigns;
}
