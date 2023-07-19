package com.example.tu_campaign_management_tool_api.payload.request;

import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class CampaignRequest {

    @Size(max = 40)
    @Getter
    private String campaignId;

    @Size(max = 40)
    @Getter
    private Integer campaignFolderId;

    @NotNull
    @Size(max = 40)
    @Getter
    private String title;

    @NotNull
    @Getter
    private Date startDate;

    @NotNull
    @Getter
    private Date endDate;

    @Size(max = 38)
    @Getter
    private int type;

    @NotNull
    @Size(max = 1000)
    @Getter
    private String promoDescriptionText;

    @NotNull
    @Size(max = 500)
    @Getter
    private String promoSummaryText;

    @NotNull
    @Size(max = 20)
    @Getter
    private String ribbonType;

    @Size(max = 40)
    @Getter
    private String termsUrl;

    @Getter
    private Set<String> campaignClientGroups;

    @Getter
    private Set<String> campaignTags;

    @Getter
    private boolean rootIndicator;

    @NotNull
    @Size(max = 700)
    @Getter
    private String filterImgUrl;

    @NotNull
    @Size(max = 20)
    @Getter
    private String filterOverlayText;

    @NotNull
    @Size(max = 700)
    @Getter
    private String promoImgUrl;

    @NotNull
    @Size(max = 20)
    @Getter
    private String promoImgAltText;

    @Size(max = 700)
    @Getter
    private String campaignWebsiteUrl;

    @Size(max = 40)
    @Getter
    private String campaignWebsiteText;

    @Size(max = 70)
    @Getter
    private String appTitle;

    @Size(max = 700)
    @Getter
    private String appImageUrl;

    @Size(max = 500)
    @Getter
    private String appSummary;

    @Size(max = 700)
    @Getter
    private String relativeUrl;

    @NotNull
    @Getter
    private List<CampaignItem> campaignItems;
}
