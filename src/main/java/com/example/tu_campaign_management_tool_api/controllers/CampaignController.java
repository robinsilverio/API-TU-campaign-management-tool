package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import com.example.tu_campaign_management_tool_api.payload.request.CampaignsRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.MessageResponse;
import com.example.tu_campaign_management_tool_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

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

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> retrieveCampaigns() {
        List<Campaign> retrievedCampaigns = campaignRepository.findAll();
        CampaignsMappingResponse response = new CampaignsMappingResponse(retrievedCampaigns);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> createCampaign() {
        return ResponseEntity.ok("Create campaign");
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> updateCampaign() {
        return ResponseEntity.ok("Update campaign");
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> deleteCampaigns(@RequestBody CampaignsRequest campaignsRequest) {
        for (Campaign campaign : campaignsRequest.getCampaigns()) {
            campaignRepository.deleteByCampaignId(campaign.getCampaignId());
        }
        return ResponseEntity.ok(new MessageResponse("Selected campaigns are deleted."));
    }

    @DeleteMapping("/delete/{campaignId}")
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> deleteCampaign(@PathVariable String campaignId) {
        if (campaignRepository.existsByCampaignId(campaignId)) {

            Campaign campaign = campaignRepository.findCampaignByCampaignId(campaignId);
            List<CampaignItem> campaignItemsBelongingToACampaign = List.copyOf(campaign.getCampaignItems());

            campaign.getCampaignItems().clear(); // Clearing the association
            campaign.getCampaignClientGroups().clear(); // Clearing the association
            campaign.getCampaignTags().clear(); // Clearing the association

            campaignRepository.delete(campaign); // Deleting the campaign

            campaignItemsBelongingToACampaign.forEach(campaignItem -> {
                List<CampaignDiscount> campaignDiscountsBelongingToCampaignItem = List.copyOf(campaignItem.getCampaignDiscounts());
                campaignItem.getCampaignDiscounts().clear();
                campaignItemRepository.delete(campaignItem);
                campaignDiscountsBelongingToCampaignItem.forEach(campaignDiscount -> {
                    if (campaignDiscount.getDiscountPrice() != null) {
                        System.out.println("DiscountPrice is available "  + campaignDiscount.getDiscountPrice());
                        campaignDiscountPriceRepository.delete(campaignDiscount.getDiscountPrice());
                        campaignDiscount.setDiscountPrice(null);
                    } else if (campaignDiscount.getDiscountPercentage() != null) {
                        System.out.println("DiscountPercentage is available " + campaignDiscount.getDiscountPercentage());
                        campaignDiscountPercentageRepository.delete(campaignDiscount.getDiscountPercentage());
                        campaignDiscount.setDiscountPercentage(null);
                    }
                    campaignDiscountRepository.delete(campaignDiscount);
                });
            });
            return ResponseEntity.ok(new MessageResponse("Campaign deleted."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Campaign to be deleted not found."));
        }
    }

}
