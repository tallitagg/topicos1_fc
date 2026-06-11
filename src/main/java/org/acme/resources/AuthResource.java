package org.acme.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AuthDTO;
import org.acme.exceptions.UnauthorizedException;
import org.acme.model.Usuario;
import org.acme.service.HashService;
import org.acme.service.JwtService;
import org.acme.service.UsuarioService;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthDTO dto) {
        String hash = hashService.getHashSenha(dto.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null)
            throw new UnauthorizedException("Credenciais inválidas");

        String token = jwtService.generateJwt(usuario.getUsername(), usuario.getPerfil());

        return Response.ok().header("Authorization", token).build();

    }
}