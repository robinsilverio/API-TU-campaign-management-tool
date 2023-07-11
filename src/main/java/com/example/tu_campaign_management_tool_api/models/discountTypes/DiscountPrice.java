package com.example.tu_campaign_management_tool_api.models.discountTypes;

import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WEB_CMP_DISCOUNT_PRICE")
@NoArgsConstructor
@AllArgsConstructor
public class DiscountPrice {

    @Id
    @Column(name = "discount_id")
    @Getter
    private String discountId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "discount_id")
    @Getter
    private CampaignDiscount campaignDiscount;

    @NotNull
    @Column(name = "price")
    @Getter
    private Double price;

}

