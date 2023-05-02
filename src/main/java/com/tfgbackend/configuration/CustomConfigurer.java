package com.tfgbackend.configuration;

import com.tfgbackend.filters.AuthenticationFilter;
import com.tfgbackend.filters.AuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.security.Key;

public class CustomConfigurer extends AbstractHttpConfigurer<CustomConfigurer, HttpSecurity> {

    private final Key key;

    public CustomConfigurer(Key key){
        this.key = key;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(new AuthenticationFilter(authenticationManager, key))
            .addFilter(new AuthorizationFilter(authenticationManager, key));
    }

    public static CustomConfigurer customDsl(Key key) {
        return new CustomConfigurer(key);
    }
}
