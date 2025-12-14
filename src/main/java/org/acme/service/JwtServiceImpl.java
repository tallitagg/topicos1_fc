package org.acme.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Perfil;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {


    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(String login, Perfil perfil) {

        // data
        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        // papeis (perfil)
        Set<String> roles = new HashSet<String>();
        roles.add(perfil.name());

        // gerando o token
        return Jwt.issuer("topicos_1")
                .subject(login)
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();

    }

}
