package com.example.tu_campaign_management_tool_api.controllers;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.payload.responses.CampaignsMappingResponse;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
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
    private CampaignRepository campaignRepository;

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

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> deleteCampaign() {
        return ResponseEntity.ok("Delete campaign");
    }

}
