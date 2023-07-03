package com.example.tu_campaign_management_tool_api.security.services;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String id;
    private String login;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String paramId, String paramLogin, String paramPwd,
                           Collection<? extends GrantedAuthority> paramAuthorities) {
        this.id = paramId;
        this.login = paramLogin;
        this.password = paramPwd;
        this.authorities = paramAuthorities;
    }

    public static UserDetailsImpl build(AuthUser user) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
