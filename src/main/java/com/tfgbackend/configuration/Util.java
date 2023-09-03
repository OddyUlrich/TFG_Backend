package com.tfgbackend.configuration;


import java.security.Key;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Util {

    // Establecemos unha duración para os tokens
    private static final Duration TOKEN_DURATION = Duration.ofMinutes(1);

    //Recibimos por argumento la lista de roles asignados al usuario
    public static Cookie successfulCookieAuthentication(List<String> authorities, Duration rememberDuration, String email, Key key){

        // Almacenamos o momento actual
        long now = System.currentTimeMillis();

        // Creamos o token JWT empregando o builder
        JwtBuilder tokenBuilder = Jwts.builder()
                // Establecemos como "propietario" do token ao usuario (o seu email) que fixo login
                .setSubject(email)
                // Establecemos a data de emisión do token
                .setIssuedAt(new Date(now))
                // Establecemos a data máxima de validez do token
                .setExpiration(new Date(now + TOKEN_DURATION.toMillis() + rememberDuration.toMillis()))
                // Engadimos un atributo máis ao corpo do token cos roles do usuario
                .claim("roles", authorities)
                // Asinamos o token coa nosa clave secreta
                .signWith(key);

        // Engadimos o token á resposta nunha cookie de "Authentication"
        Cookie jwtTokenCookie = new Cookie("Authentication", tokenBuilder.compact());
        jwtTokenCookie.setMaxAge((int)TOKEN_DURATION.toSeconds() + (int)rememberDuration.toSeconds());
        jwtTokenCookie.setHttpOnly(true);
        return jwtTokenCookie;
    }
}
