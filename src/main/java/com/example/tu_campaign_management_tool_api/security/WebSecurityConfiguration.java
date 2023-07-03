package com.example.tu_campaign_management_tool_api.security;

import com.example.tu_campaign_management_tool_api.security.jwt.AuthEntryPointJwt;
import com.example.tu_campaign_management_tool_api.security.jwt.AuthTokenFilter;
import com.example.tu_campaign_management_tool_api.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
}
