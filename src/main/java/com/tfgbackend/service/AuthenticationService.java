package com.tfgbackend.service;

import com.tfgbackend.model.User;
import com.tfgbackend.model.enumerators.Rol;
import com.tfgbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UserRepository users;

    @Autowired
    public AuthenticationService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos o usuario correspondente ao id proporcionado na base de datos,
        // e lanzamos a excepción no caso de que non exista
        User user = users.findUserById(username).orElseThrow(() -> new UsernameNotFoundException(username));

        // Creamos o usuario de spring empregando o builder
        return org.springframework.security.core.userdetails.User.builder()
                // Establecemos o nome do usuario
                .username(user.getEmail())
                // Establecemos o contrasinal do usuario
                .password(user.getPassword())
                // Establecemos a lista de roles que ten o usuario.
                // Por convenio, os roles sempre teñen o prefixo "ROLE_"
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(
                        user.getRols().stream().map(Rol::toString).collect(Collectors.joining(","))
                ))
                // Xeneramos o obxecto do usuario a partir dos datos introducidos no builder
                .build();
    }
}
