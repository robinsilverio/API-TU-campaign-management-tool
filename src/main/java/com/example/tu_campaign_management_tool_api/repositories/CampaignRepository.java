package com.example.tu_campaign_management_tool_api.repositories;

import com.example.tu_campaign_management_tool_api.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Campaign findCampaignByCampaignId(String campaignId);
    @Transactional
    void deleteByCampaignId(String campaignId);

    boolean existsByCampaignId(String campaignId);
}
