package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ModeloDTO;
import org.acme.service.ModeloService;

import java.util.logging.Logger;

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    private static final Logger LOG = Logger.getLogger(ModeloResource.class.getName());

    @Inject
    ModeloService modeloService;

    @GET
    @RolesAllowed({"USER","ADM"})
    public Response buscarTodos() {
        LOG.info("ModeloResource#buscarTodas chamado");
        return Response.ok(modeloService.findAll()).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("ModeloResource#buscarPorNome chamado - nome=" + nome);
        return Response.ok(modeloService.findByNome(nome)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/marca/{marcaNome}")
    public Response buscarPorMarca(@PathParam("marcaNome") String marca) {
        LOG.info("ModeloResource#buscarPorMarca chamado - marca=" + marca);
        return Response.ok(modeloService.findByMarcaNome(marca)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/ano/{anoLancamento}")
    public Response buscarPorAnoLancamento(@PathParam("anoLancamento") Integer anoLancamento) {
        LOG.info("ModeloResource#buscarPorAnoLancamento chamado - Ano de Lançamento=" + anoLancamento);
        return Response.ok(modeloService.findByAnoLancamento(anoLancamento)).build();
    }

    @POST
    @RolesAllowed({"ADM"})
    public Response incluir(ModeloDTO dto) {
        LOG.info("ModeloResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(modeloService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response atualizar(@PathParam("id") Long id, ModeloDTO dto) {
        LOG.info("ModeloResource#alterar chamado - id=" + id + ", dto=" + dto);
        modeloService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADM"})
    public Response deletar(@PathParam("id") Long id) {
        LOG.info("ModeloResource#excluir chamado - id=" + id);
        modeloService.delete(id);
        return Response.noContent().build();
    }
}
