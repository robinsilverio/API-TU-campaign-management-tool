package com.example.tu_campaign_management_tool_api.repositories;

import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignDiscountRepository extends JpaRepository<CampaignDiscount, Long> {
    Optional<CampaignDiscount> findCampaignDiscountByDiscountId(String discountId);
}
