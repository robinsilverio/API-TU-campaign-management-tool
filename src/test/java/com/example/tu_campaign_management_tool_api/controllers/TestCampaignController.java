package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.MessageResponse;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCampaignController {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignRepository repository;
    private int expectedStatusCode;
    private String campaignId;
    private int expectedSize;
    private int actualSize;
    private int actualStatusCode;
    private String actualResponseMessage;

    private void arrangeSize(int paramSize) {
        this.expectedSize = paramSize;
    }

    private void arrangeStatusCode(int paramStatusCode) {
        this.expectedStatusCode = paramStatusCode;
    }

    private void arrangeCampaignId() {
        this.campaignId = "abcdef";
    }

    private void stubbingFindAllFunction(List<Campaign> toBeReturnedCampaigns) {
        when(this.repository.findAll()).thenReturn(toBeReturnedCampaigns);
    }

    private void stubbingFindCampaignByCampaignId(boolean toBeReturnedBoolean) {
        when(this.repository.existsByCampaignId(this.campaignId)).thenReturn(toBeReturnedBoolean);
    }

    private void stubbingDeleteByCampaignId() {
        doNothing().when(this.repository).deleteByCampaignId(this.campaignId);
    }

    public void actActualSize() {
        CampaignsMappingResponse campaignsMappingResponse = (CampaignsMappingResponse) this.campaignController.retrieveCampaigns().getBody();
        this.actualSize = campaignsMappingResponse.getCampaigns().size();
    }

    private void actStatusCodeOnRetrieval() {
        ResponseEntity<?> responseEntity = this.campaignController.retrieveCampaigns();
        this.actualStatusCode = responseEntity.getStatusCode().value();
    }

    private void actStatusCodeOnDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaign(this.campaignId);
        this.actualStatusCode = responseEntity.getStatusCode().value();
    }

    private void actActualResponseMessage() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaign(this.campaignId);
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        actualResponseMessage = messageResponse.getMessage();
    }

    @Test
    public void should_return_statusCode_200_when_retrieving_campaigns() {
        // Arrange
        arrangeStatusCode(200);
        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(new Campaign(), new Campaign(), new Campaign()));
        // Act
        stubbingFindAllFunction(campaigns);
        actStatusCodeOnRetrieval();
        // Assert
        assertThat(this.actualStatusCode, is(this.expectedStatusCode));
    }

    @Test
    public void should_return_three_campaigns_when_retrieving() {
        // Arrange
        arrangeSize(3);
        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(new Campaign(), new Campaign(), new Campaign()));
        // Act
        stubbingFindAllFunction(campaigns);
        actActualSize();
        // Assert
        assertThat(actualSize, is(expectedSize));
    }

    @Test
    public void should_return_empty_campaignList_when_retrieving(){
        // Arrange
        arrangeSize(0);
        // Act
        stubbingFindAllFunction(new ArrayList<>(Arrays.asList()));
        actActualSize();
        // Assert
        assertThat(actualSize, is(expectedSize));
    }

    @Test
    public void should_return_statusCode_200_when_campaign_is_deleted() {
        // Arrange
        arrangeStatusCode(200);
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(true);
        stubbingDeleteByCampaignId();
        actStatusCodeOnDeletion();
        // Assert
        assertThat(actualStatusCode, is(this.expectedStatusCode));
    }

    @Test
    public void should_return_statusCode_404_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        arrangeStatusCode(404);
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(false);
        actStatusCodeOnDeletion();
        // Assert
        assertThat(this.actualStatusCode, is(this.expectedStatusCode));
    }

    @Test
    public void should_return_confirmation_message_when_campaign_is_deleted() {
        // Arrange
        String expectedErrorMessage = "Campaign deleted";
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(true);
        stubbingDeleteByCampaignId();
        actActualResponseMessage();
        // Assert
        assertThat(this.actualResponseMessage, is(expectedErrorMessage));
    }

    @Test
    public void should_return_an_errorMessage_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        String expectedErrorMessage = "Campaign to be deleted not found";
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(false);
        actActualResponseMessage();
        // Assert
        assertThat(this.actualResponseMessage, is(expectedErrorMessage));
    }

}
