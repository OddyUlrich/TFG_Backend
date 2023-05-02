package com.tfgbackend.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final Key key;

    public AuthorizationFilter(AuthenticationManager manager, Key key){
        super(manager);
        this.key = key;
    }

    // Método a executar cando se comproba o control de acceso
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                chain.doFilter(request, response);
                return;
            }

            // Find the cookie with the cookie name for the JWT token
            for (Cookie cookie : cookies) {
                if (!cookie.getName().equals("Authentication")) {
                    continue;
                }
                // No caso de que o token sexa un JWT, comprobamos que sexa valido
                UsernamePasswordAuthenticationToken authentication = getAuthentication(cookie.getValue());

                // Se o token era válido, establecemolo no contexto de seguridade de Spring para poder empregalo
                // nos nosos servizos
                SecurityContextHolder.getContext().setAuthentication(authentication);

                break;
            }

            chain.doFilter(request, response);

        } catch(ExpiredJwtException e){
            // Se se sobrepasou a duración do token devolvemos un erro 419.
            response.setStatus(419);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws ExpiredJwtException {
        // Creamos un parser para o token coa clave de firmado da nosa aplicación
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                // Parseamos o corpo do token
                .parseClaimsJws(token)
                .getBody();

        // Obtemos o nome do propietario do token
        String user = claims.getSubject();

        // Obtemos o listado de roles do usuario
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("roles").toString());

        // Devolvemos o token interno de Spring, que será engadido no contexto.
        return user == null ? null : new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

}