package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CorDTO;
import org.acme.service.CorService;

import java.util.logging.Logger;

@Path("/cores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorResource {

    @Inject
    CorService corService;

    private static final Logger LOG = Logger.getLogger(CorResource.class.getName());

    @GET
    @RolesAllowed({"USER", "ADM"})
    public Response buscarTodas() {
        LOG.info("CorResource#buscarTodas chamado");
        return Response.ok(corService.findAll()).build();
    }

    @GET
    @Path("/{nome}")
    @RolesAllowed({"USER", "ADM"})
    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("CorResource#buscarPorNome chamado - nome=" + nome);
        return Response.ok(corService.findByName(nome)).build();
    }

    @POST
    @RolesAllowed({"ADM"})
    public Response incluir(CorDTO dto) {
        LOG.info("CorResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(corService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response alterar(@PathParam("id") Long id, CorDTO dto) {
        LOG.info("CorResource#alterar chamado - id=" + id + ", dto=" + dto);
        corService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("CorResource#excluir chamado - id=" + id);
        corService.delete(id);
        return Response.noContent().build();
    }

}
