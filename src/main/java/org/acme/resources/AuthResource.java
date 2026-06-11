package org.acme.resources;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AlterarSenhaDTO;
import org.acme.dto.AuthDTO;
import org.acme.dto.AuthResponseDTO;
import org.acme.dto.UsuarioResponseDTO;
import org.acme.model.Usuario;
import org.acme.service.HashService;
import org.acme.service.JwtService;
import org.acme.service.UsuarioService;
import org.eclipse.microprofile.jwt.JsonWebToken;

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

    @Inject
    JsonWebToken jwt;

    @POST
    public Response loginAntigo(@Valid AuthDTO dto) {
        return gerarRespostaLogin(dto);
    }

    @POST
    @Path("/login")
    public Response login(@Valid AuthDTO dto) {
        return gerarRespostaLogin(dto);
    }

    @GET
    @Path("/me")
    @Authenticated
    public Response me() {
        UsuarioResponseDTO usuario = usuarioService.findResponseByUsername(jwt.getSubject());

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado.", Response.Status.UNAUTHORIZED);
        }

        return Response.ok(usuario).build();
    }

    @PUT
    @Path("/alterar-senha")
    @Authenticated
    public Response alterarSenha(@Valid AlterarSenhaDTO dto) {
        usuarioService.alterarSenha(jwt.getSubject(), dto);

        return Response.noContent().build();
    }

    private Response gerarRespostaLogin(AuthDTO dto) {
        String hash = hashService.getHashSenha(dto.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null) {
            throw new WebApplicationException("Credenciais inválidas.", Response.Status.UNAUTHORIZED);
        }

        String token = jwtService.generateJwt(usuario.getUsername(), usuario.getPerfil());

        return Response.ok(new AuthResponseDTO(token, "Bearer"))
                .header("Authorization", token)
                .build();
    }
}