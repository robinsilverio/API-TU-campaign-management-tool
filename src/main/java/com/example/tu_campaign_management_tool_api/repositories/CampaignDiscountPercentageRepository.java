package com.example.tu_campaign_management_tool_api.repositories;

import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignDiscountPercentageRepository extends JpaRepository<DiscountPercentage, Long> {
}
