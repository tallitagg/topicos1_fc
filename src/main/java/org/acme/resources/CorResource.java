package org.acme.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CorDTO;
import org.acme.service.CorService;

import java.util.logging.Logger;

@Path("cores")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorResource {

    @Inject
    CorService corService;

    private static final Logger LOG = Logger.getLogger(CorResource.class.getName());

    @GET
    @PermitAll
    public Response buscarTodas() {
        LOG.info("CorResource#buscarTodas chamado");
        return Response.ok(corService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response buscarPorId(@PathParam("id") Long id) {
    return Response.ok(corService.findById(id)).build();
    }

    @GET
    @Path("/nome/{nome}")
    @PermitAll
    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("CorResource#buscarPorNome chamado - nome=" + nome);
        return Response.ok(corService.findByName(nome)).build();
    }

    @POST
    @PermitAll
    public Response incluir(CorDTO dto) {
        LOG.info("CorResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(corService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @PermitAll
    public Response alterar(@PathParam("id") Long id, CorDTO dto) {
        LOG.info("CorResource#alterar chamado - id=" + id + ", dto=" + dto);
        corService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @PermitAll
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("CorResource#excluir chamado - id=" + id);
        corService.delete(id);
        return Response.noContent().build();
    }

}
