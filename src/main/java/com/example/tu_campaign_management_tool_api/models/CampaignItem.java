package com.example.tu_campaign_management_tool_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "WEB_CMP_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class CampaignItem {
    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.example.tu_campaign_management_tool_api.generator.NextIdGenerator")
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

    @Column(nullable = true)
    @Size(max = 100)
    @Getter
    private String promoImgAltText;

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

    @ManyToMany(mappedBy = "campaignItems", cascade = CascadeType.REMOVE)
    @Setter
    private List<Campaign> campaigns;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "WEB_CMP_ITEM_DISCOUNT",
            joinColumns = @JoinColumn(name = "campaign_item_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id")
    )
    @Getter
    @Setter
    private List<CampaignDiscount> campaignDiscounts;

}
