package org.acme.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ProdutoDTO;
import org.acme.service.ProdutoService;

import java.util.logging.Logger;

@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    private static final Logger LOG = Logger.getLogger(ProdutoResource.class.getName());

    @Inject
    ProdutoService produtoService;

    @GET
    @RolesAllowed({"USER","ADM"})
    public Response buscarTodos() {
        LOG.info("ProdutoResource#buscarTodas chamado");
        return Response.ok(produtoService.findAll()).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) {
        LOG.info("ProdutoResource#buscarPorNome chamado - nome=" + nome);
        return Response.ok(produtoService.findByNome(nome)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/preco/{preco}")
    public Response buscarPorPreco(@PathParam("preco") Long preco) {
        LOG.info("ProdutoResource#buscarPorPreco chamado - preco=" + preco);
        return Response.ok(produtoService.findByPreco(preco)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("/marca/{marca}")
    public Response buscarPorMarca(@PathParam("marca") String marca) {
        LOG.info("ProdutoResource#buscarPorMarca chamado - marca=" + marca);
        return Response.ok(produtoService.findByMarca(marca)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("modelo/{modelo}")
    public Response buscarPorModelo(@PathParam("modelo") String modelo) {
        LOG.info("ProdutoResource#buscarPorModelo chamado - modelo=" + modelo);
        return Response.ok(produtoService.findByModelo(modelo)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("material/{material}")
    public Response buscarPorMaterial(@PathParam("material") String material) {
        LOG.info("ProdutoResource#buscarPorMaterial chamado - material=" + material);
        return Response.ok(produtoService.findByMaterial(material)).build();
    }

    @GET
    @RolesAllowed({"USER","ADM"})
    @Path("capacidade/{capacidade}")
    public Response buscarPorCapacidade(@PathParam("capacidade") Double capacidade) {
        LOG.info("ProdutoResource#buscarPorCapacidade chamado - capacidade=" + capacidade);
        return Response.ok(produtoService.findByCapacidade(capacidade)).build();
    }

    @POST
    @RolesAllowed({"ADM"})
    public Response incluir(ProdutoDTO dto) {
        LOG.info("ProdutoResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(produtoService.create(dto)).build();
    }

    @PUT
    @RolesAllowed({"ADM"})
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, ProdutoDTO dto) {
        LOG.info("ProdutoResource#alterar chamado - id=" + id + ", dto=" + dto);
        produtoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"ADM"})
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("ProdutoResource#excluir chamado - id=" + id);
        produtoService.delete(id);
        return Response.noContent().build();
    }
}
