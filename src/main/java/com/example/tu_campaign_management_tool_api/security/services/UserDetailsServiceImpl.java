package com.example.tu_campaign_management_tool_api.security.services;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    protected UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Gebruiker niet gevonden met de gebruikersnaam: " + username));

        return UserDetailsImpl.build(user);
    }
}
