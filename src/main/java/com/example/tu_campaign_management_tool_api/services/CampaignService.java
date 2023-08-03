package com.example.tu_campaign_management_tool_api.services;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import com.example.tu_campaign_management_tool_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignItemRepository campaignItemRepository;

    @Autowired
    private CampaignDiscountRepository campaignDiscountRepository;

    @Autowired
    private CampaignDiscountPriceRepository campaignDiscountPriceRepository;

    @Autowired
    private CampaignDiscountPercentageRepository campaignDiscountPercentageRepository;


    public List<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    public Campaign createCampaign(Campaign paramCampaignRequest) {
        return campaignRepository.save(paramCampaignRequest);
    }

    public Campaign updateCampaign(Campaign paramCampaignRequest) {

        Optional<Campaign> retrievedOptionalCampaign = campaignRepository.findCampaignByCampaignId(paramCampaignRequest.getCampaignId());

        if (!retrievedOptionalCampaign.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found");
        }

        Campaign retrievedCampaign = retrievedOptionalCampaign.get();

        // Perform campaign update
        retrievedCampaign.setTitle(paramCampaignRequest.getTitle());
        retrievedCampaign.setStartDate(paramCampaignRequest.getStartDate());
        retrievedCampaign.setEndDate(paramCampaignRequest.getEndDate());
        retrievedCampaign.setPromoDescriptionText(paramCampaignRequest.getPromoDescriptionText());
        retrievedCampaign.setPromoSummaryText(paramCampaignRequest.getPromoSummaryText());
        retrievedCampaign.setRibbonType(paramCampaignRequest.getRibbonType());
        retrievedCampaign.setTermsUrl(paramCampaignRequest.getTermsUrl());
        retrievedCampaign.setCampaignClientGroups(paramCampaignRequest.getCampaignClientGroups());
        retrievedCampaign.setCampaignTags(paramCampaignRequest.getCampaignTags());
        retrievedCampaign.setRootIndicator(paramCampaignRequest.isRootIndicator());
        retrievedCampaign.setFilterImgUrl(paramCampaignRequest.getFilterImgUrl());
        retrievedCampaign.setFilterOverlayText(paramCampaignRequest.getFilterOverlayText());
        retrievedCampaign.setPromoImgUrl(paramCampaignRequest.getPromoImgUrl());
        retrievedCampaign.setPromoImgAltText(paramCampaignRequest.getPromoImgAltText());
        retrievedCampaign.setCampaignWebsiteUrl(paramCampaignRequest.getCampaignWebsiteUrl());
        retrievedCampaign.setCampaignWebsiteText(paramCampaignRequest.getCampaignWebsiteText());
        retrievedCampaign.setAppTitle(paramCampaignRequest.getAppTitle());
        retrievedCampaign.setAppImageUrl(paramCampaignRequest.getAppImageUrl());
        retrievedCampaign.setAppSummary(paramCampaignRequest.getAppSummary());
        retrievedCampaign.setRelativeUrl(paramCampaignRequest.getRelativeUrl());
        retrievedCampaign.setCampaignItems(paramCampaignRequest.getCampaignItems());

        return campaignRepository.save(retrievedCampaign);
    }

    public void deleteCampaign(String paramCampaignId) {
        Optional<Campaign> campaignOpt = campaignRepository.findCampaignByCampaignId(paramCampaignId);
        if (!campaignOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found.");
        }
        Campaign campaign = campaignOpt.get();
        List<CampaignItem> campaignItemsBelongingToACampaign = List.copyOf(campaign.getCampaignItems());

        campaign.getCampaignItems().clear(); // Clearing the association
        campaign.getCampaignClientGroups().clear(); // Clearing the association
        campaign.getCampaignTags().clear(); // Clearing the association

        campaignRepository.delete(campaign); // Deleting the campaign

        campaignItemsBelongingToACampaign.forEach(campaignItem -> {
            List<CampaignDiscount> campaignItemDiscountsBelongingToCampaignItem = List.copyOf(campaignItem.getCampaignItemDiscounts());
            campaignItem.getCampaignItemDiscounts().clear();
            campaignItemRepository.delete(campaignItem);
            campaignItemDiscountsBelongingToCampaignItem.forEach(campaignDiscount -> {
                if (campaignDiscount.getDiscountPrice() != null) {
                    campaignDiscountPriceRepository.delete(campaignDiscount.getDiscountPrice());
                    campaignDiscount.setDiscountPrice(null);
                } else if (campaignDiscount.getDiscountPercentage() != null) {
                    campaignDiscountPercentageRepository.delete(campaignDiscount.getDiscountPercentage());
                    campaignDiscount.setDiscountPercentage(null);
                }
                campaignDiscountRepository.delete(campaignDiscount);
            });
        });
    }
}
