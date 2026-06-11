package org.acme.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

import org.acme.dto.ProdutoDTO;
import org.acme.service.ArquivoDownload;
import org.acme.service.ProdutoFileServiceImpl;
import org.acme.service.ProdutoService;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.logging.Logger;

@Path("produtos")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    private static final Logger LOG = Logger.getLogger(ProdutoResource.class.getName());

    @Inject
    ProdutoService produtoService;

    @Inject
    ProdutoFileServiceImpl fileService;

    @GET
    @PermitAll
    public Response buscarTodos() {
        LOG.info("ProdutoResource#buscarTodas chamado");
        return Response.ok(produtoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(produtoService.findById(id)).build();
    }

    @GET
    @PermitAll
    @Path("/preco/{preco}")
    public Response buscarPorPreco(@PathParam("preco") Long preco) {
        LOG.info("ProdutoResource#buscarPorPreco chamado - preco=" + preco);
        return Response.ok(produtoService.findByPreco(preco)).build();
    }

    @GET
    @PermitAll
    @Path("/marca/{marca}")
    public Response buscarPorMarca(@PathParam("marca") String marca) {
        LOG.info("ProdutoResource#buscarPorMarca chamado - marca=" + marca);
        return Response.ok(produtoService.findByMarca(marca)).build();
    }

    @GET
    @PermitAll
    @Path("modelo/{modelo}")
    public Response buscarPorModelo(@PathParam("modelo") String modelo) {
        LOG.info("ProdutoResource#buscarPorModelo chamado - modelo=" + modelo);
        return Response.ok(produtoService.findByModelo(modelo)).build();
    }

    @GET
    @PermitAll
    @Path("material/{material}")
    public Response buscarPorMaterial(@PathParam("material") String material) {
        LOG.info("ProdutoResource#buscarPorMaterial chamado - material=" + material);
        return Response.ok(produtoService.findByMaterial(material)).build();
    }

    @GET
    @PermitAll
    @Path("capacidade/{capacidade}")
    public Response buscarPorCapacidade(@PathParam("capacidade") Double capacidade) {
        LOG.info("ProdutoResource#buscarPorCapacidade chamado - capacidade=" + capacidade);
        return Response.ok(produtoService.findByCapacidade(capacidade)).build();
    }

    @POST
    @PermitAll
    public Response incluir(ProdutoDTO dto) {
        LOG.info("ProdutoResource#incluir chamado - dto=" + dto);
        return Response.status(Response.Status.CREATED).entity(produtoService.create(dto)).build();
    }

    @PUT
    @PermitAll
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, ProdutoDTO dto) {
        LOG.info("ProdutoResource#alterar chamado - id=" + id + ", dto=" + dto);
        produtoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @PermitAll
    @Path("/{id}")
    public Response excluir(@PathParam("id") Long id) {
        LOG.info("ProdutoResource#excluir chamado - id=" + id);
        produtoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/image/download/{fid}")
    @PermitAll
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("fid") String fid) {
        ArquivoDownload download = fileService.download(fid);

        ResponseBuilder response = Response.ok(download.content(), download.contentType());
        response.header("Content-Disposition", "attachment; filename=\"" + download.fileName().replace("\"", "") + "\"");

        return response.build();
    }

    @PATCH
    @Path("/image/upload")
    @PermitAll
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(
            @RestForm("idProduto")
            @NotNull(message = "idProduto é obrigatório.")
            @Min(value = 1, message = "idProduto deve ser maior ou igual a 1.")
            Long idProduto,

            @RestForm("file")
            @NotNull(message = "Arquivo de imagem é obrigatório.")
            FileUpload file) {

        try {
            fileService.salvar(idProduto, file);
            return Response.noContent().build();
        } catch (IOException e) {
            return Response.status(Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/image/{fid}")
    @PermitAll
    public Response removerImagem(@PathParam("fid") String fid) {
        fileService.remover(fid);
        return Response.noContent().build();
    }
}