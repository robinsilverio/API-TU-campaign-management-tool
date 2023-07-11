package com.example.tu_campaign_management_tool_api.models.discountTypes;

import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WEB_CMP_DISCOUNT_PCT")
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPercentage {
    @Id
    @Column(name = "discount_id")
    @Getter
    private String discountId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private CampaignDiscount campaignDiscount;

    @NotNull
    @Column(name = "percentage")
    @Getter
    private Double percentage;
}
