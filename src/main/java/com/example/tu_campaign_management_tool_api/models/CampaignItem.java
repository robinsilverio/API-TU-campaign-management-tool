package com.example.tu_campaign_management_tool_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "WEB_CMP_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class CampaignItem {
    @Id
    @GeneratedValue
    @Size(max = 40)
    @Getter
    private String campaignItemId;

    @NotNull
    @Size(max = 40)
    @Getter
    private String promoTitle;

    @NotNull
    @Size(max = 50)
    @Getter
    private String promoText;

    @NotNull
    @Size(max = 300)
    @Getter
    private String promoImgUrl;

    @NotNull
    @Size(max = 1)
    @Getter
    private String weight;

    @NotNull
    @Size(max = 20)
    @Getter
    private String teaser;

    @NotNull
    @Size(max = 15)
    @Getter
    private String extraText;

    @ManyToMany(mappedBy = "campaignItems")
    private List<Campaign> campaigns;

    @ManyToMany()
    @JoinTable(
            name = "WEB_CMP_ITEM_DISCOUNT",
            joinColumns = @JoinColumn(name = "campaign_item_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    private List<CampaignDiscount> campaignDiscounts;

}
