package com.example.tu_campaign_management_tool_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_CMP_USER")
public class AuthUser {
    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.example.tu_campaign_management_tool_api.generator.NextIdGenerator")
    @Size(max = 40)
    @Getter
    private String id;

    @NotNull
    @Size(max = 40)
    @Getter
    private String login;

    @NotNull
    @Getter
    private String password;

    @Column(name = "ROLE")
    @Getter
    @Size(max = 40)
    private String role;

}