package com.example.tu_campaign_management_tool_api.models;

import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPercentage;
import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPrice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "WEB_CMP_DISCOUNT")
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDiscount {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.example.tu_campaign_management_tool_api.generator.NextIdGenerator")
    @Size(max = 40)
    @Getter
    private String discountId;

    @Column(nullable = true)
    @Getter
    private int tuPoints;

    @Column(nullable = true)
    @Size(max = 700)
    @Getter
    private String altSkuImgUrl;

    @ManyToMany(mappedBy = "campaignItemDiscounts")
    private List<CampaignItem> campaignItems;

    @ElementCollection
    @CollectionTable(name = "WEB_CMP_DISCOUNT_SKU", joinColumns = @JoinColumn(name = "discount_id"))
    @Column(name = "sku_id")
    @Getter
    private Set<String> skuIds;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @Getter
    @Setter
    private DiscountPrice discountPrice;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @Getter
    @Setter
    private DiscountPercentage discountPercentage;

}
