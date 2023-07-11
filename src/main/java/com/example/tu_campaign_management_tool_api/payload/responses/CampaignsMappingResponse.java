package com.example.tu_campaign_management_tool_api.payload.responses;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import lombok.Getter;

import java.util.List;

public class CampaignsMappingResponse {
    @Getter
    private List<Campaign> campaigns;

    public CampaignsMappingResponse(List<Campaign> paramCampaigns) {
        this.campaigns = paramCampaigns;
    }
}
