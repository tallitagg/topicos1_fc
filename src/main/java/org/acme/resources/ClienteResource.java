package org.acme.resources;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.AtualizarPerfilDTO;
import org.acme.dto.ClienteDTO;
import org.acme.dto.ClienteResponseDTO;
import org.acme.service.ClienteService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@Path("clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed({"USER", "ADM"})
    public List<ClienteResponseDTO> listAll() {
        return clienteService.findAll();
    }

    @GET
    @Path("/me")
    @Authenticated
    public ClienteResponseDTO meuPerfil() {
        return clienteService.findMeuPerfil(jwt.getSubject());
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER", "ADM"})
    public ClienteResponseDTO findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @GET
    @Path("/username/{username}")
    @RolesAllowed({"ADM"})
    public Response findByUsername(@PathParam("username") String username) {
        return Response.ok(clienteService.findByUsername(username)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public ClienteResponseDTO update(@PathParam("id") Long id, ClienteDTO dto) {
        return clienteService.update(id, dto);
    }

    @PUT
    @Path("/perfil")
    @Authenticated
    public ClienteResponseDTO atualizarPerfilCliente(@Valid AtualizarPerfilDTO dto) {
        return clienteService.atualizarPerfilCliente(jwt.getSubject(), dto);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);

        return Response.noContent().build();
    }
}