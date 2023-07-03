package com.example.tu_campaign_management_tool_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"WEB_CMP_USER\"")
public class AuthUser {
    @Id
    @GeneratedValue
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