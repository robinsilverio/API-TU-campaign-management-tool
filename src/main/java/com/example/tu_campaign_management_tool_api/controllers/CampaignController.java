package com.example.tu_campaign_management_tool_api.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/campaign")
public class CampaignController {


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_SUPER_USER_E-SALES')")
    public ResponseEntity<?> retrieveCampaigns() {
        return ResponseEntity.ok("Retrieve campaign");
    }

    @PostMapping
    @PreAuthorize("hasRole('Super User e-Sales')")
    public ResponseEntity<?> createCampaign() {
        return ResponseEntity.ok("Create campaign");
    }

    @PutMapping
    @PreAuthorize("hasRole('Super User e-Sales')")
    public ResponseEntity<?> updateCampaign() {
        return ResponseEntity.ok("Update campaign");
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Super User e-Sales')")
    public ResponseEntity<?> deleteCampaign() {
        return ResponseEntity.ok("Delete campaign");
    }

}
