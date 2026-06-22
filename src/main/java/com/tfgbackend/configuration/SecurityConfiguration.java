package com.tfgbackend.configuration;

import com.tfgbackend.filter.LoginFilter;
import com.tfgbackend.filter.AuthenticationFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final SignatureKeyJWT signatureKeyJWT;

    @Autowired
    public SecurityConfiguration(SignatureKeyJWT signatureKeyJWT) {
        this.signatureKeyJWT = signatureKeyJWT;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        LoginFilter authFilter = new LoginFilter(authManager, tokenSignKey());

        http
            .cors(cors -> {})
            .csrf(AbstractHttpConfigurer::disable)
            // Indicamos que por defecto permitimos o acceso a login por parte de calqueira
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup", "/error", "/users/check").permitAll()
                // Calquera outra peticion necesita, minimo, el rol de usuario
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.
                authenticationEntryPoint((request, response, authException) ->
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                )
                .accessDeniedHandler((request, response, accessDeniedException) ->
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN)
                )
            )
            // Especificamos que queremos sesións sen estado (REST é, por definición, sen estado)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Engadimos os nosos filtros á cadea de filtros das chamadas
            .addFilter(authFilter)
            .addFilterBefore(new AuthenticationFilter(tokenSignKey()), UsernamePasswordAuthenticationFilter.class);

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
        // Creamos unha instancia do algoritmo BCrypt para empregar como encoder de contrasinais
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        // Creamos a nosa xerarquia de roles a partir do map definido previamente
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_TEACHER\n ROLE_ADMIN > ROLE_STUDENT");

        return hierarchy;
    }

    @Bean
    public Key tokenSignKey() {
        if (this.signatureKeyJWT.getSecret() != null && !this.signatureKeyJWT.getSecret().isBlank()) {
            return Keys.hmacShaKeyFor(this.signatureKeyJWT.getSecret().getBytes(StandardCharsets.UTF_8));
        } else {
            return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }
}
