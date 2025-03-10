package com.blautech.auth_microservice.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.blautech.auth_microservice.entity.User;

public class CustomUserDetails implements UserDetails {

    private Integer id;
    private String username;
    private String password;

    public CustomUserDetails(User userCredentials) {
        id = userCredentials.getId();
        this.username = userCredentials.getEmail();
        this.password = userCredentials.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Integer getId(){
        return id;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
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
