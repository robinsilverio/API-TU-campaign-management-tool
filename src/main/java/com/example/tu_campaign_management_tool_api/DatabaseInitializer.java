package com.example.tu_campaign_management_tool_api;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import com.example.tu_campaign_management_tool_api.models.Campaign;
import com.example.tu_campaign_management_tool_api.models.CampaignDiscount;
import com.example.tu_campaign_management_tool_api.models.CampaignItem;
import com.example.tu_campaign_management_tool_api.models.discountTypes.DiscountPrice;
import com.example.tu_campaign_management_tool_api.repositories.CampaignRepository;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @PostConstruct
    public void loadUser() {
        AuthUser user = new AuthUser(null, "T14832", "$2a$10$VcdzH8Q.o4KEo6df.XesdOmXdXQwT5ugNQvu1Pl0390rmfOeA1bhS", "ROLE_SUPER_USER_E-SALES");
        AuthUser userTwo = new AuthUser(null, "E122005", "$2a$10$VcdzH8Q.o4KEo6df.XesdOmXdXQwT5ugNQvu1Pl0390rmfOeA1bhS", "ROLE_SUPER_USER_IT");
        userRepository.saveAll(Arrays.asList(user, userTwo));
    }
    @PostConstruct
    public void loadCampaigns() {

        final int AMOUNT_OF_CAMPAIGNS = 3;

        for (int i = 0; i < AMOUNT_OF_CAMPAIGNS; i++) {

            LocalDateTime dateNow = LocalDateTime.now();
            Date startDate = Date.from(dateNow.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(dateNow.plusDays(10).atZone(ZoneId.systemDefault()).toInstant());
            Campaign campaign = new Campaign(
                    null,
                    null,
                    "Construction Product Campaign 00000" + (i + 1),
                    startDate,
                    endDate,
                    9001,
                    "Shop now for a wide range of construction products at discounted prices. Limited stock available!",
                    "Save Big on Construction Products",
                    "Special Offer",
                    "abc",
                    new HashSet<>(Arrays.asList("REST")),
                    null,
                    true,
                    "filter_image.jpg",
                    "Limited Time Offer",
                    "promo_image.jpg",
                    "abc",
                    "https://constructionproductscampaign.com",
                    "Learn More",
                    "Construction Products App",
                    "app_image.jpg",
                    "Explore and purchase construction products on the go. Get exclusive discounts and offers!",
                    "campaign-actie/page",
                    Arrays.asList()
            );

            CampaignItem campaignItem = new CampaignItem(
                    null,
                    "Product 000001000001",
                    "Promo text for Product 00000100000" + (i + 1),
                    "product_image1.jpg",
                    null,
                    "M",
                    "Teaser 1",
                    "Extra Text 1",
                    Arrays.asList(campaign),
                    null
            );

            CampaignDiscount campaignDiscount = new CampaignDiscount(
                    null,
                    5,
                    null,
                    Arrays.asList(campaignItem),
                    new HashSet<>(Arrays.asList("00032123", "2239221")),
                    null,
                    null
            );

            DiscountPrice discountPrice = new DiscountPrice(null, campaignDiscount, 20.0);
            campaignDiscount.setDiscountPrice(discountPrice);

            campaignItem.setCampaignItemDiscounts(Arrays.asList(campaignDiscount));
            campaign.setCampaignItems(Arrays.asList(campaignItem));
            this.campaignRepository.save(campaign);
        }
    }
}
