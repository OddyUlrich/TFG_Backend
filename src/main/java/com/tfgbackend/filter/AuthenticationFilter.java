package com.tfgbackend.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.Key;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.tfgbackend.configuration.Util.successfulCookieAuthentication;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager manager;
    private final Key key;
    private boolean remember;

    public AuthenticationFilter(AuthenticationManager manager, Key key){
        this.manager = manager;
        this.key = key;
    }

    // Método que tenta autenticar ao usuario a partir da chamada HTTP
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Obtemos o obxecto JSON do body da request HTTP
            JsonNode credentials = new ObjectMapper().readValue(request.getInputStream(), JsonNode.class);

            remember = (credentials.get("remember") != null) ? credentials.get("remember").booleanValue() : false;

            // Tentamos autenticarnos coas credenciais proporcionadas
            return manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.get("email").textValue(),
                            credentials.get("password").textValue()
                    )
            );
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    // Método que se chama cando a autenticación do metodo anterior é satisfactoria
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        Duration rememberDuration;

        // If the user checked "remember me" we extend the duration of the token for 30 days
        if (remember){
            rememberDuration = Duration.ofDays(30);
        }else{
            rememberDuration = Duration.ofDays(0);
        }

        // Obtemos a lista de roles asignados ao usuario e concatenamolso nun string separado por comas
        List<String> authorities = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //Obtemos o nome de usuario que fixo login
        String username = ((User)authResult.getPrincipal()).getUsername();

        Cookie jwtTokenCookie = successfulCookieAuthentication(authorities, rememberDuration, username, key);
        response.addCookie(jwtTokenCookie);

    }

}
