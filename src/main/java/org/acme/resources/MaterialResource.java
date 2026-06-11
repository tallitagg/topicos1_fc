package org.acme.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.MaterialDTO;
import org.acme.service.MaterialService;
import jakarta.validation.Valid;

import java.util.logging.Logger;

@Path("materiais")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    private static final Logger LOG = Logger.getLogger(MaterialResource.class.getName());

    @Inject
    MaterialService materialService;

    @GET
    @PermitAll
    public Response buscarTodos() {
        LOG.info("MaterialResource#buscarTodas chamado");
        return Response.ok(materialService.findAll()).build();
    }

    @GET
    @Path("/tipo/{tipo}")
    @PermitAll
    public Response buscarPorTipo(@PathParam("tipo") String tipo) {
        LOG.info("MaterialResource#buscarPorNome chamado - tipo=" + tipo);
        return Response.ok(materialService.findByTipo(tipo)).build();
    }

    @POST
    @PermitAll
    public Response incluir(@Valid MaterialDTO dto) {
        LOG.info("MaterialResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(materialService.create(dto)).build();
    }
    @GET
    @Path("/{id}")
    @PermitAll
    public Response buscarPorId(@PathParam("id") Long id) {
    return Response.ok(materialService.findById(id)).build();
}

    @PUT
    @Path("/{id}")
    @PermitAll
    public Response atualizar(@PathParam("id") Long id, @Valid MaterialDTO dto) {
        LOG.info("MaterialResource#alterar chamado - id=" + id + ", dto=" + dto);
        materialService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @PermitAll
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("MaterialResource#excluir chamado - id=" + id);
        materialService.delete(id);
        return Response.noContent().build();
    }
}
