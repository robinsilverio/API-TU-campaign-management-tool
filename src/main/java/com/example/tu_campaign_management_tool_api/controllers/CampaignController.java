package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.payload.request.SelectedCampaignsRequest;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.payload.responses.MessageResponse;
import com.example.tu_campaign_management_tool_api.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    @Autowired
    public CampaignService campaignService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> retrieveCampaigns() {
        List<Campaign> retrievedCampaigns = campaignService.findAll();
        CampaignsMappingResponse response = new CampaignsMappingResponse(retrievedCampaigns);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> createCampaign(@RequestBody Campaign paramCampaignRequest) {
        campaignService.createCampaign(paramCampaignRequest);
        return ResponseEntity.ok("Create campaign");
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> updateCampaign() {
        return ResponseEntity.ok("Update campaign");
    }

    @DeleteMapping("/delete/selected-campaigns")
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> deleteSelectedCampaigns(@RequestBody SelectedCampaignsRequest paramSelectedCampaignsRequest) {
        for (Campaign campaign : paramSelectedCampaignsRequest.getSelectedCampaigns()) {
            campaignService.deleteCampaign(campaign.getCampaignId());
        }
        return ResponseEntity.ok(new MessageResponse("Selected campaigns are deleted."));
    }

    @DeleteMapping("/delete/{paramCampaignId}")
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> deleteCampaign(@PathVariable String paramCampaignId) {
        campaignService.deleteCampaign(paramCampaignId);
        return ResponseEntity.ok(new MessageResponse("Campaign deleted."));
    }

}
