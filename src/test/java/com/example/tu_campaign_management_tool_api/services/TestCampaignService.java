package com.example.tu_campaign_management_tool_api.services;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPercentage;
import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPrice;
import com.example.tu_campaign_management_tool_api.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCampaignService {

    @InjectMocks
    private CampaignService campaignService;
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private CampaignItemRepository campaignItemRepository;
    @Mock
    private CampaignDiscountRepository campaignDiscountRepository;
    @Mock
    private CampaignDiscountPriceRepository campaignDiscountPriceRepository;
    @Mock
    private CampaignDiscountPercentageRepository campaignDiscountPercentageRepository;
    private ArrayList<Campaign> campaigns;
    private int expectedSize;
    private List<Campaign> retrievedCampaigns;
    private int actualSize;
    private Campaign campaign;
    private ResponseStatusException responseStatusExceptionThrown;
    private List<CampaignItem> campaignItems;

    private void arrangeCampaignsAndSize(List<Campaign> paramCampaigns) {
        this.campaigns = new ArrayList<>(paramCampaigns);
        this.expectedSize = this.campaigns.size();
    }

    private void arrangeCampaignItemsAndDiscounts() {
        this.campaignItems = new ArrayList<>(asList(new CampaignItem()));

        CampaignDiscount campaignDiscount = new CampaignDiscount();
        List<CampaignDiscount> campaignDiscounts = new ArrayList<>(asList(campaignDiscount));

        this.campaignItems.get(0).setCampaigns(asList(this.campaign));
        this.campaignItems.get(0).setCampaignItemDiscounts(campaignDiscounts);
    }

    private void arrangeCampaign() {
        String paramCampaignId = "campaign-id-1";
        this.campaign = new Campaign();
        this.campaign.setCampaignId(paramCampaignId);
        this.campaign.setCampaignClientGroups(new HashSet<>(asList("REST", "INDUSTRIE")));

        arrangeCampaignItemsAndDiscounts();

        this.campaign.setCampaignItems(campaignItems);
        this.campaign.setCampaignTags(new HashSet<>(asList("Merk")));
    }

    private void actActualSize() {
        when(this.campaignRepository.findAll()).thenReturn(this.campaigns);
        this.retrievedCampaigns = this.campaignService.findAll();
        this.actualSize = this.retrievedCampaigns.size();
    }

    private void actCampaignDeletion() {
        when(this.campaignRepository.findCampaignByCampaignId(this.campaign.getCampaignId())).thenReturn(Optional.of(this.campaign));
        this.campaignService.deleteCampaign(this.campaign.getCampaignId());
    }

    private void actThrowableResponseStatusException() {
        when(this.campaignRepository.findCampaignByCampaignId(this.campaign.getCampaignId())).thenReturn(Optional.empty());
        this.responseStatusExceptionThrown = assertThrows(ResponseStatusException.class, () -> {
            this.campaignService.deleteCampaign(this.campaign.getCampaignId());
        });
    }

    @Test
    public void should_return_a_list_of_campaigns_after_retrieval() {
        // Arrange
        arrangeCampaignsAndSize(asList(new Campaign(), new Campaign(), new Campaign()));
        // Act
        actActualSize();
        // Assert
        assertThat(this.actualSize, is(this.expectedSize));
    }

    @Test
    public void should_return_an_empty_list_of_campaigns_after_retrieval() {
        // Arrange
        arrangeCampaignsAndSize(List.of());
        // Act
        actActualSize();
        // Assert
        assertThat(this.actualSize, is(this.expectedSize));
    }

    @Test
    public void should_return_a_campaign_after_creation() {
        // Arrange
        Campaign expectedCampaign = new Campaign();
        expectedCampaign.setTitle("Test campaign");
        // Act
        when(this.campaignRepository.save(expectedCampaign)).thenReturn(expectedCampaign);
        Campaign actualCampaign = this.campaignService.createCampaign(expectedCampaign);
        // Assert
        assertNotNull(actualCampaign);
    }

    @Test
    public void should_delete_campaign_after_performing_remove_operation() {
        // Arrange
        arrangeCampaign();
        // Act
        actCampaignDeletion();
        // Assert
        verify(this.campaignRepository).delete(campaign);
    }

    @Test
    public void should_delete_campaignItems_when_performing_remove_operation() {
        // Arrange
        arrangeCampaign();
        List<CampaignItem> campaignItems = List.copyOf(this.campaign.getCampaignItems());
        // Act
        actCampaignDeletion();
        // Assert
        verify(this.campaignItemRepository).delete(campaignItems.get(0));
    }

    @Test
    public void should_delete_discountPrice_when_performing_remove_operation() {
        // Arrange
        arrangeCampaign();
        List<CampaignDiscount> campaignDiscounts = this.campaign.getCampaignItems().get(0).getCampaignItemDiscounts();
        DiscountPrice discountPrice = new DiscountPrice();
        campaignDiscounts.get(0).setDiscountPrice(discountPrice);
        // Act
        actCampaignDeletion();
        // Assert
        verify(this.campaignDiscountPriceRepository).delete(discountPrice); // Verify deletion of the DiscountPrice
    }

    @Test
    public void should_delete_discountPercentage_when_performing_remove_operation() {
        // Arrange
        arrangeCampaign();
        List<CampaignDiscount> campaignDiscounts = this.campaign.getCampaignItems().get(0).getCampaignItemDiscounts();
        DiscountPercentage discountPercentage = new DiscountPercentage();
        campaignDiscounts.get(0).setDiscountPercentage(discountPercentage);
        // Act
        actCampaignDeletion();
        // Assert
        verify(this.campaignDiscountPercentageRepository).delete(discountPercentage);
    }

    @Test
    public void should_trigger_responseStatusException_if_campaign_isNonExistent() {
        // Arrange
        arrangeCampaign();
        // Act
        actThrowableResponseStatusException();
        // Assert
        assertThat(this.responseStatusExceptionThrown, instanceOf(ResponseStatusException.class));
    }

    @Test
    public void should_return_an_errorMessage_if_campaign_isNonExistent() {
        // Arrange
        String expectedErrorMessage =  "404 NOT_FOUND \"Campaign not found.\"";
        arrangeCampaign();
        // Act
        actThrowableResponseStatusException();
        String actualErrorMessage = responseStatusExceptionThrown.getMessage();
        // Assert
        assertThat(actualErrorMessage, is(expectedErrorMessage));
    }

}
