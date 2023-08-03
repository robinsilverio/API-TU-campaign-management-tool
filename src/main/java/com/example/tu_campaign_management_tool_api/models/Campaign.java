package com.example.tu_campaign_management_tool_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_CMP_CAMPAIGN")
public class Campaign {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.example.tu_campaign_management_tool_api.generator.NextIdGenerator")
    @Size(max = 40)
    @Getter
    @Setter
    private String campaignId;

    @Column(nullable = true)
    @Getter
    private Integer campaignFolderId;

    @NotNull
    @Size(max = 40)
    @Getter
    @Setter
    private String title;

    @NotNull
    @Getter
    @Setter
    private Date startDate;

    @NotNull
    @Getter
    @Setter
    private Date endDate;

    @Getter
    @Column(name = "type", length = 38)
    private int type;

    @NotNull
    @Size(max = 1000)
    @Getter
    @Setter
    private String promoDescriptionText;

    @NotNull
    @Size(max = 500)
    @Getter
    @Setter
    private String promoSummaryText;

    @NotNull
    @Size(max = 20)
    @Getter
    @Setter
    private String ribbonType;

    @Column(nullable = true)
    @Size(max = 40)
    @Getter
    @Setter
    private String termsUrl;

    @ElementCollection
    @CollectionTable(name = "WEB_CMP_CAMPAIGN_CLIENT_GROUP", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "client_group_id")
    @Getter
    @Setter
    private Set<String> campaignClientGroups;

    @ElementCollection
    @CollectionTable(name = "WEB_CMP_CAMPAIGN_LABEL", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "label_text")
    @Getter
    @Setter
    private Set<String> campaignTags;

    @Column(nullable = true, columnDefinition = "NUMBER(1,0) DEFAULT 0")
    @Getter
    @Setter
    private boolean rootIndicator;

    @Column(nullable = true)
    @Size(max = 700)
    @Getter
    @Setter
    private String filterImgUrl;

    @Column(nullable = true)
    @Size(max = 20)
    @Getter
    @Setter
    private String filterOverlayText;

    @NotNull
    @Size(max = 700)
    @Getter
    @Setter
    private String promoImgUrl;

    @NotNull
    @Size(max = 20)
    @Getter
    @Setter
    private String promoImgAltText;

    @Column(nullable = true)
    @Size(max = 700)
    @Getter
    @Setter
    private String campaignWebsiteUrl;

    @Column(nullable = true)
    @Size(max = 40)
    @Getter
    @Setter
    private String campaignWebsiteText;

    @Column(nullable = true)
    @Size(max = 70)
    @Getter
    @Setter
    private String appTitle;

    @Column(nullable = true)
    @Size(max = 700)
    @Getter
    @Setter
    private String appImageUrl;

    @Column(nullable = true)
    @Size(max = 500)
    @Getter
    @Setter
    private String appSummary;

    @Column(nullable = true)
    @Size(max = 700)
    @Getter
    @Setter
    private String relativeUrl;

    @NotNull
    @Getter
    @Setter
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "WEB_CMP_CAMPAIGN_ITEM",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "campaign_item_id")
    )
    private List<CampaignItem> campaignItems;

}
