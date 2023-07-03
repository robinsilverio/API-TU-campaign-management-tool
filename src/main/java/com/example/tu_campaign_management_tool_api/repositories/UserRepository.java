package com.example.tu_campaign_management_tool_api.repositories;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByLogin(String login);
    boolean existsByLogin(String login);

}
