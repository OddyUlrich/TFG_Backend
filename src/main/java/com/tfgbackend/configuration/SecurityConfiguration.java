package com.tfgbackend.configuration;

import com.tfgbackend.filters.AuthenticationFilter;
import com.tfgbackend.filters.AuthorizationFilter;
import com.tfgbackend.service.AuthenticationService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.security.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationService auth;

    @Autowired
    public SecurityConfiguration(AuthenticationService auth) {
        this.auth = auth;
    }
    //TODO QUITAMOS AUTH de aquí, no? Iba a quitar el servicio como tal, pero se usa internamente por lo que entiendo (?)


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        http.csrf().disable()
                // Indicamos que por defecto permitimos o acceso de calquera a todos os servizos
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                // Engadimos os nosos filtros á cadea de filtros das chamadas
                .addFilter(new AuthenticationFilter(authenticationManager, tokenSignKey()))
                .addFilter(new AuthorizationFilter(authenticationManager, tokenSignKey()))
                // Especificamos que queremos sesións sen estado (REST é, por definición, sen estado)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Establecemos a xerarquia de roles
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());
        return (web) -> web.expressionHandler(handler);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        // Creamos unha instancia do algoritmo BCrypt para empregar como encoder
        // de contrasinais
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        Map<String, List<String>> roles = new HashMap<>();
        // Definimos a nosa xerarquia de roles nun map
        // Os valores representan os roles incluidos no rol especificado como clave
        roles.put("ROLE_ADMIN", Collections.singletonList("ROLE_USER"));

        // Creamos a nosa xerarquia de roles a partir do map definido previamente
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(RoleHierarchyUtils.roleHierarchyFromMap(roles));

        return hierarchy;
    }

    @Bean
    public Key tokenSignKey() {
        // Xeramos unha clave de firmado aleatoria para o algoritmo SHA512
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
