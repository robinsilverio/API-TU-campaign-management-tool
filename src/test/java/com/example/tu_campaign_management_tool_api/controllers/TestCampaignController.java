package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.payload.request.CampaignsRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.MessageResponse;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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
    private final int EXPECTED_STATUS_CODE_200 = HttpStatus.OK.value();
    private final int EXPECTED_STATUS_CODE_404 = HttpStatus.NOT_FOUND.value();
    private String campaignId;
    private int expectedSize;
    private int actualSize;
    private int actualStatusCode;
    private String actualResponseMessage;
    private Campaign campaignOne;
    private Campaign campaignTwo;
    private Campaign campaignThree;
    private ArrayList<Campaign> campaigns;
    private CampaignsRequest campaignsRequest;
    private String expectedResponseMessage;


    private void arrangeResponseMessage(String paramResponseMessage) {
        this.expectedResponseMessage = paramResponseMessage;
    }

    private void arrangeSize(int paramSize) {
        this.expectedSize = paramSize;
    }

    private void arrangeCampaignId() {
        this.campaignId = "abcdef";
    }

    private void arrangeCampaigns() {
        this.campaignOne = new Campaign();
        this.campaignTwo = new Campaign();
        this.campaignThree = new Campaign();

        this.campaignOne.setCampaignId("000001");
        this.campaignTwo.setCampaignId("000002");
        this.campaignThree.setCampaignId("000003");
    }

    private void arrangeCampaignsList() {
        this.campaigns = new ArrayList<Campaign>(Arrays.asList(this.campaignOne, this.campaignTwo, this.campaignThree));
    }
    private void arrangeCampaignsRequest() {
        this.campaignsRequest = new CampaignsRequest(this.campaigns);
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

    private void actActualResponseMessageAfterSingleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaign(this.campaignId);
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        actualResponseMessage = messageResponse.getMessage();
    }

    private void actActualResponseMessageAfterMultipleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaigns(this.campaignsRequest);
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        actualResponseMessage = messageResponse.getMessage();
    }

    @Test
    public void should_return_statusCode_200_when_retrieving_campaigns() {
        // Arrange
        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(new Campaign(), new Campaign(), new Campaign()));
        // Act
        stubbingFindAllFunction(campaigns);
        actStatusCodeOnRetrieval();
        // Assert
        assertThat(this.actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_three_campaigns_when_retrieving() {
        // Arrange
        List<Campaign> campaigns = new ArrayList<>(Arrays.asList(new Campaign(), new Campaign(), new Campaign()));
        arrangeSize(campaigns.size());
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
    public void should_return_statuscode_200_when_a_set_of_campaigns_are_deleted() {
        // Arrange
        arrangeCampaigns();
        arrangeCampaignsList();
        arrangeCampaignsRequest();
        // Act
        for (Campaign campaign : this.campaigns) {
            doNothing().when(this.repository).deleteByCampaignId(campaign.getCampaignId());
        }
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaigns(this.campaignsRequest);
        this.actualStatusCode = responseEntity.getStatusCode().value();
        // Assert
        assertThat(this.actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_a_confirmationMessage_when_deleting_multiple_campaigns() {
        // Arrange
        arrangeResponseMessage("Selected campaigns are deleted.");
        arrangeCampaigns();
        arrangeCampaignsList();
        arrangeCampaignsRequest();
        // Act
        for (Campaign campaign : this.campaigns) {
            doNothing().when(this.repository).deleteByCampaignId(campaign.getCampaignId());
        }
        actActualResponseMessageAfterMultipleDeletion();
        // Assert
        assertThat(this.actualResponseMessage, is(this.expectedResponseMessage));
    }

    @Test
    public void should_return_statusCode_200_when_campaign_is_deleted() {
        // Arrange
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(true);
        stubbingDeleteByCampaignId();
        actStatusCodeOnDeletion();
        // Assert
        assertThat(actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_statusCode_404_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(false);
        actStatusCodeOnDeletion();
        // Assert
        assertThat(this.actualStatusCode, is(this.EXPECTED_STATUS_CODE_404));
    }

    @Test
    public void should_return_confirmation_message_when_campaign_is_deleted() {
        // Arrange
        arrangeResponseMessage("Campaign deleted.");
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(true);
        stubbingDeleteByCampaignId();
        actActualResponseMessageAfterSingleDeletion();
        // Assert
        assertThat(this.actualResponseMessage, is(expectedResponseMessage));
    }

    @Test
    public void should_return_an_errorMessage_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        arrangeResponseMessage("Campaign to be deleted not found.");
        arrangeCampaignId();
        // Act
        stubbingFindCampaignByCampaignId(false);
        actActualResponseMessageAfterSingleDeletion();
        // Assert
        assertThat(this.actualResponseMessage, is(expectedResponseMessage));
    }

}
