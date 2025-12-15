package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;
import org.acme.model.Pedido;
import org.acme.model.StatusPedido;
import org.acme.service.PedidoService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.logging.Logger;

@Path("pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final Logger LOG = Logger.getLogger(PedidoResource.class.getName());

    @Inject
    PedidoService pedidoService;

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed({"ADM"})
    public Response buscarTodos() {
        LOG.info("PedidoResource#buscarTodos chamado (ADM)");
        return Response.ok(pedidoService.getAll()).build();
    }

    @GET
    @Path("/meusPedidos")
    @RolesAllowed({"USER"})
    public Response buscarMinhas(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        LOG.info("PedidoResource#buscarMinhas chamado - username=" + username);
        return Response.ok(pedidoService.findByUsuario(username)).build();
    }

    @GET
    @Path("/{username}")
    @RolesAllowed({"ADM"})
    public Response buscarPorUsuario(@PathParam("username") String username) {
        LOG.info("PedidoResource#buscarPorUsuario chamado - username=" + username);
        return Response.ok(pedidoService.findByUsuario(username)).build();
    }

    @POST
    @RolesAllowed({"USER"})
    public Response incluir(PedidoDTO dto) {
        LOG.info("PedidoResource#incluir chamado - usuario=" + ", dto=" + dto);
        String username = jwt.getSubject();
        PedidoResponseDTO response = pedidoService.create(dto, username);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}/status")
    @RolesAllowed("ADM")
    public Response atualizarStatusPedido(@PathParam("id") Long idPedido, StatusPedido novoStatus) {
        PedidoResponseDTO atualizado = pedidoService.atualizarStatus(idPedido, novoStatus);
        return Response.ok(atualizado).build();
    }

}