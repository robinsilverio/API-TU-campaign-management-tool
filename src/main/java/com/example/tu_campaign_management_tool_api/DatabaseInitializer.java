package com.example.tu_campaign_management_tool_api;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadUser() {
        AuthUser user = new AuthUser(null, "T14832", "$2a$10$VcdzH8Q.o4KEo6df.XesdOmXdXQwT5ugNQvu1Pl0390rmfOeA1bhS", "ROLE_SUPER_USER_E-SALES");
        AuthUser userTwo = new AuthUser(null, "E122005", "$2a$10$VcdzH8Q.o4KEo6df.XesdOmXdXQwT5ugNQvu1Pl0390rmfOeA1bhS", "ROLE_SUPER_USER_IT");
        userRepository.saveAll(Arrays.asList(user, userTwo));
    }
}
