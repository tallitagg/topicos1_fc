package org.acme.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ClienteDTO;
import org.acme.dto.ClienteResponseDTO;
import org.acme.service.ClienteService;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Path("clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public List<ClienteResponseDTO> listAll() {
        return clienteService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER"})
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

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }
}