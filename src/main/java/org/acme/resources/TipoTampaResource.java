package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.TipoTampaDTO;
import org.acme.service.TipoTampaService;

import java.util.logging.Logger;

@Path("/tipoTampas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoTampaResource {

    private static final Logger LOG = Logger.getLogger(TipoTampaResource.class.getName());

    @Inject
    TipoTampaService tipoTampaService;

    @GET
    @RolesAllowed({"USER","ADM"})
    public Response buscarTodos() {
        LOG.info("TipoTampaResource#buscarTodas chamado");
        return Response.ok(tipoTampaService.findAll()).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/descricao/{descricao}")
    public Response buscarPorDescricao(@PathParam("descricao") String descricao) {
        LOG.info("TipoTampaResource#buscarPorDescricao chamado - descricao=" + descricao);
        return Response.ok(tipoTampaService.findByDescricao(descricao)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/material/{material}")
    public Response buscarPorMaterial(@PathParam("material") String material) {
        LOG.info("TipoTampaResource#buscarPorMaterial chamado - material=" + material);
        return Response.ok(tipoTampaService.findByMaterial(material)).build();
    }

    @POST
    @RolesAllowed({"ADM"})
    public Response incluir(TipoTampaDTO dto) {
        LOG.info("TipoTampaResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(tipoTampaService.create(dto)).build();
    }

    @PUT
    @RolesAllowed({"ADM"})
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, TipoTampaDTO dto) {
        LOG.info("TipoTampaResource#atualizar chamado - id=" + id + ", dto=" + dto);
        tipoTampaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"ADM"})
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("TipoTampaResource#excluir chamado - id=" + id);
        tipoTampaService.delete(id);
        return Response.noContent().build();
    }

}
