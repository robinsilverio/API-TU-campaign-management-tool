package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCampaignController {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignRepository repository;



    @Test
    public void should_return_three_campaigns_when_retrieving() {
        // Arrange
        int expectedSize = 3;
        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(new Campaign(), new Campaign(), new Campaign()));
        // Act
        when(repository.findAll()).thenReturn(campaigns);
        CampaignsMappingResponse campaignsMappingResponse = (CampaignsMappingResponse) this.campaignController.retrieveCampaigns().getBody();
        int actualSize = campaignsMappingResponse.getCampaigns().size();
        // Assert
        assertThat(actualSize, is(expectedSize));
    }

    @Test
    public void should_return_empty_campaignList_when_retrieving(){
        // Arrange
        int expectedSize = 0;
        // Act
        when(repository.findAll()).thenReturn(new ArrayList<>(Arrays.asList()));
        CampaignsMappingResponse campaignsMappingResponse = (CampaignsMappingResponse) this.campaignController.retrieveCampaigns().getBody();
        int actualSize = campaignsMappingResponse.getCampaigns().size();
        // Assert
        assertThat(actualSize, is(expectedSize));
    }

}
