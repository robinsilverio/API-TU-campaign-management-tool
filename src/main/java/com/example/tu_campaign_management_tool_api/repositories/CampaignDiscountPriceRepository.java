package com.example.tu_campaign_management_tool_api.repositories;

import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignDiscountPriceRepository extends JpaRepository<DiscountPrice, Long> {
}
