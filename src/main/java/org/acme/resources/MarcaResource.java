package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.MarcaDTO;
import org.acme.service.MarcaService;

import java.util.logging.Logger;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    private static final Logger LOG = Logger.getLogger(MarcaResource.class.getName());

    @GET
    @RolesAllowed({"USER", "ADM"})
    public Response buscarTodas() {
        LOG.info("MarcaResource#buscarTodas chamado");
        return Response.ok(marcaService.findAll()).build();
    }

    @GET
    @Path("/{nome}")
    @RolesAllowed({"USER", "ADM"})
    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("MarcaResource#buscarPorNome chamado");
        return Response.ok(marcaService.findByName(nome)).build();
    }

    @POST
    @RolesAllowed({"ADM"})
    public Response incluir(MarcaDTO dto) {
        LOG.info("MarcaResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response alterar(@PathParam("id") Long id, MarcaDTO dto) {
        LOG.info("MarcaResource#alterar chamado - id=" + id + ", dto=" + dto);
        marcaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("MarcaResource#excluir chamado - id=" + id);
        marcaService.delete(id);
        return Response.noContent().build();
    }

}
