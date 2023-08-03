package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPrice;
import com.example.tu_campaign_management_tool_api.payload.request.SelectedCampaignsRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.MessageResponse;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
import com.example.tu_campaign_management_tool_api.services.CampaignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCampaignController {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignService campaignService;

    @Mock
    private CampaignRepository campaignRepository;

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
    private SelectedCampaignsRequest selectedCampaignsRequest;
    private String expectedResponseMessage;
    private Campaign campaign;
    private CampaignDiscount campaignDiscount;
    private CampaignItem campaignItem;


    private void arrangeResponseMessage(String paramResponseMessage) {
        this.expectedResponseMessage = paramResponseMessage;
    }

    private void arrangeSize(int paramSize) {
        this.expectedSize = paramSize;
    }

    private void arrangeCampaignId() {
        this.campaignId = "abcdef";
    }

    public void arrangeCampaign(
            String paramId,
            String paramTitle,
            Date paramStartDate,
            Date paramEndDate,
            int paramType,
            String paramPromoDescription,
            String paramPromoSummary,
            String paramRibbonType,
            String paramTermsUrl,
            Set<String> paramClientGroups,
            Set<String> paramTags,
            boolean paramRootIndicator,
            String paramFilterImageUrl,
            String paramFilterOverlayText,
            String paramPromoImgUrl,
            String paramPromoImgAltText,
            String paramCampaignWebsiteUrl,
            String paramCampaignWebsiteText,
            String paramAppTitle,
            String paramAppImageUrl,
            String paramAppSummary,
            String paramRelativeUrl,
            List<CampaignItem> paramCampaignItems
    ) {
        this.campaign = new Campaign(paramId,
                null,
                paramTitle,
                paramStartDate,
                paramEndDate,
                paramType,
                paramPromoDescription,
                paramPromoSummary,
                paramRibbonType,
                paramTermsUrl,
                paramClientGroups,
                paramTags,
                paramRootIndicator,
                paramFilterImageUrl,
                paramFilterOverlayText,
                paramPromoImgUrl,
                paramPromoImgAltText,
                paramCampaignWebsiteUrl,
                paramCampaignWebsiteText,
                paramAppTitle,
                paramAppImageUrl,
                paramAppSummary,
                paramRelativeUrl,
                paramCampaignItems
        );
    }

    public void arrangeCampaignItem(
            String paramId,
            String paramTitle,
            String paramPromoText,
            String paramPromoImgUrl,
            String paramPromoImgAltText,
            String paramPromoWeight,
            String paramTeaser,
            String paramExtraText
    ) {
        this.campaignItem = new CampaignItem(
                paramId,
                paramTitle,
                paramPromoText,
                paramPromoImgUrl,
                paramPromoImgAltText,
                paramPromoWeight,
                paramTeaser,
                paramExtraText,
                null,
                null
        );
    }

    public void arrangeCampaignDiscount(
            String paramId,
            int paramTuPoints,
            String paramAltSkuImgUrl,
            CampaignItem paramCampaignItem,
            String[] paramSkus
    ) {
        campaignDiscount = new CampaignDiscount(
                paramId,
                paramTuPoints,
                paramAltSkuImgUrl,
                Arrays.asList(paramCampaignItem),
                new HashSet<>(Arrays.asList(paramSkus)),
                null,
                null
        );
    }

    public void arrangeAllComponentsRelatedToCampaigns() {
        arrangeCampaignItem(
                null,
                "Product 000001000001",
                "Promo text for Product 000001000001",
                "product_image1.jpg",
                null,
                "M",
                "Teaser 1",
                "Extra Text 1"
        );

        arrangeCampaignDiscount(
                null,
                5,
                null,
                campaignItem,
                new String[]{"00032123", "2239221"}
        );

        DiscountPrice discountPrice = new DiscountPrice(null, this.campaignDiscount, 20.0);
        this.campaignDiscount.setDiscountPrice(discountPrice);

        this.campaignItem.setCampaigns(Arrays.asList(this.campaign));
        this.campaignItem.setCampaignItemDiscounts(Arrays.asList(this.campaignDiscount));
        this.campaign.setCampaignItems(Arrays.asList(this.campaignItem));
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
        this.selectedCampaignsRequest = new SelectedCampaignsRequest(this.campaigns);
    }

    private void stubbingFindAllFunction(List<Campaign> toBeReturnedCampaigns) {
        when(this.campaignService.findAll()).thenReturn(toBeReturnedCampaigns);
    }

    private void stubbingFindCampaignByCampaignId(Long toBeReturnedOneCampaign) {
        when(this.campaignService.findAll().stream().filter(campaign -> campaign.getCampaignId() == this.campaignId).count()).thenReturn(toBeReturnedOneCampaign);
    }

    private void stubbingDeleteByCampaignId(String paramCampaignId) {
        doNothing().when(this.campaignService).deleteCampaign(paramCampaignId);
    }

    public void actActualSize() {
        CampaignsMappingResponse campaignsMappingResponse = (CampaignsMappingResponse) this.campaignController.retrieveCampaigns().getBody();
        this.actualSize = campaignsMappingResponse.getCampaigns().size();
    }

    private void actStatusCodeOnRetrieval() {
        ResponseEntity<?> responseEntity = this.campaignController.retrieveCampaigns();
        this.actualStatusCode = responseEntity.getStatusCode().value();
    }

    private void actStatusCodeOnSingleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaign(this.campaignId);
        this.actualStatusCode = responseEntity.getStatusCode().value();
    }

    public void actStatusCodeOnMultipleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteSelectedCampaigns(this.selectedCampaignsRequest);
        this.actualStatusCode = responseEntity.getStatusCode().value();
    }

    private void actActualResponseMessageAfterSingleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteCampaign(this.campaignId);
        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        actualResponseMessage = messageResponse.getMessage();
    }

    private void actActualResponseMessageAfterMultipleDeletion() {
        ResponseEntity<?> responseEntity = this.campaignController.deleteSelectedCampaigns(this.selectedCampaignsRequest);
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
    public void should_return_statusCode_200_when_a_campaign_is_added() {
        // Arrange.
        arrangeCampaign(
                null,
                "Construction Product Campaign 000003",
                Date.from(LocalDateTime.of(2023, 7, 20, 10, 30, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2023, 8, 20, 10, 30, 0).atZone(ZoneId.systemDefault()).toInstant()),
                9001,
                "Shop now for a wide range of construction products at discounted prices. Limited stock available!",
                "Save Big on Construction Products",
                "Special Offer",
                "abc",
                new HashSet<>(Arrays.asList("Industrie")),
                new HashSet<>(),
                true,
                "filter_image.jpg",
                "Limited Time Offer!",
                "promo_image.jpg",
                "abc",
                "https://constructionproductscampaign.com",
                "Learn More",
                "Construction Products App",
                "app_image.jpg",
                "Explore and purchase construction products on the go. Get exclusive discounts and offers!",
                null,
                Arrays.asList());
        arrangeAllComponentsRelatedToCampaigns();
        // Act
        when(this.campaignService.createCampaign(this.campaign)).thenReturn(this.campaign);
        ResponseEntity<?> response = this.campaignController.createCampaign(this.campaign);
        this.actualStatusCode = response.getStatusCode().value();
        // Assert
        assertThat(this.actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_statusCode200_when_campaign_mutation_went_successfully() {
        // Arrange
        arrangeCampaign(
                null,
                "Construction Product Campaign 000003",
                Date.from(LocalDateTime.of(2023, 7, 20, 10, 30, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2023, 8, 20, 10, 30, 0).atZone(ZoneId.systemDefault()).toInstant()),
                9001,
                "Shop now for a wide range of construction products at discounted prices. Limited stock available!",
                "Save Big on Construction Products",
                "Special Offer",
                "abc",
                new HashSet<>(Arrays.asList("Industrie")),
                new HashSet<>(),
                true,
                "filter_image.jpg",
                "Limited Time Offer!",
                "promo_image.jpg",
                "abc",
                "https://constructionproductscampaign.com",
                "Learn More",
                "Construction Products App",
                "app_image.jpg",
                "Explore and purchase construction products on the go. Get exclusive discounts and offers!",
                null,
                Arrays.asList());
        arrangeAllComponentsRelatedToCampaigns();
        // Act
        when(this.campaignService.updateCampaign(this.campaign)).thenReturn(this.campaign);
        ResponseEntity<?> response = this.campaignController.updateCampaign(this.campaign);
        this.actualStatusCode = response.getStatusCode().value();
        // Assert
        assertThat(this.actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_statuscode_200_when_a_set_of_campaigns_are_deleted() {
        // Arrange
        arrangeCampaigns();
        arrangeCampaignsList();
        arrangeCampaignsRequest();
        // Act
        for (Campaign campaign : this.campaigns) {
            stubbingDeleteByCampaignId(campaign.getCampaignId());
        }
        actStatusCodeOnMultipleDeletion();
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
            stubbingDeleteByCampaignId(campaign.getCampaignId());
        }
        actActualResponseMessageAfterMultipleDeletion();
        // Assert
        assertThat(this.actualResponseMessage, is(this.expectedResponseMessage));
    }

    @Test
    public void should_return_statusCode_200_when_campaign_is_deleted() {
        // Arrange
        arrangeCampaignId();
        Campaign campaign = new Campaign();
        campaign.setCampaignId(this.campaignId);
        // Act
        stubbingDeleteByCampaignId(campaign.getCampaignId());
        actStatusCodeOnSingleDeletion();
        // Assert
        assertThat(actualStatusCode, is(this.EXPECTED_STATUS_CODE_200));
    }

    @Test
    public void should_return_confirmation_message_when_campaign_is_deleted() {
        // Arrange
        arrangeResponseMessage("Campaign deleted.");
        arrangeCampaignId();
        Campaign campaign = new Campaign();
        campaign.setCampaignId(this.campaignId);
        // Act
        stubbingDeleteByCampaignId(campaign.getCampaignId());
        actActualResponseMessageAfterSingleDeletion();
        // Assert
        assertThat(this.actualResponseMessage, is(expectedResponseMessage));
    }

    @Test
    public void should_return_statusCode_404_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        arrangeCampaignId();
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found."))
                .when(campaignService)
                .deleteCampaign(this.campaignId);

        // Act
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = campaignController.deleteCampaign(this.campaignId);
        } catch (ResponseStatusException e) {
            // Assert
            assertThat(e.getStatusCode().value(), is(this.EXPECTED_STATUS_CODE_404));
        }
    }

    @Test
    public void should_return_an_errorMessage_when_campaign_does_not_exist_during_deletion() {
        // Arrange
        arrangeCampaignId();
        arrangeResponseMessage("404 NOT_FOUND \"Campaign not found.\"");
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found."))
                .when(campaignService)
                .deleteCampaign(this.campaignId);

        // Act
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = campaignController.deleteCampaign(this.campaignId);
        } catch (ResponseStatusException e) {
            // Assert
            assertThat(e.getMessage(), is(this.expectedResponseMessage));
        }
    }

}
