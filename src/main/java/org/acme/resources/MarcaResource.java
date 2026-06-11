package org.acme.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.MarcaDTO;
import org.acme.service.MarcaService;
import org.acme.dto.ClienteResponseDTO;
import java.util.logging.Logger;
import jakarta.validation.Valid;

@Path("marcas")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    private static final Logger LOG = Logger.getLogger(MarcaResource.class.getName());

    @GET
    @PermitAll
    public Response buscarTodas() {
        LOG.info("MarcaResource#buscarTodas chamado");
        return Response.ok(marcaService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response buscarPorId(@PathParam("id") Long id) {
    return Response.ok(marcaService.findById(id)).build();
}

    @GET
    @PermitAll
    @Path("/busca/{nome}")

    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("MarcaResource#buscarPorNome chamado");
        return Response.ok(marcaService.findByName(nome)).build();
    }

    @POST
    @PermitAll
    public Response incluir(@Valid MarcaDTO dto) {
        LOG.info("MarcaResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
   @PermitAll
    public Response alterar(@PathParam("id") Long id, @Valid MarcaDTO dto) {
        LOG.info("MarcaResource#alterar chamado - id=" + id + ", dto=" + dto);
        marcaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @PermitAll
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("MarcaResource#excluir chamado - id=" + id);
        marcaService.delete(id);
        return Response.noContent().build();
    }

}
