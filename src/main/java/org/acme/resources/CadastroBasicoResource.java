package org.acme.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.CadastroBasicoDTO;
import org.acme.service.CadastroBasicoService;

import java.util.logging.Logger;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/cadastroBasicoCliente")
public class CadastroBasicoResource {

    @Inject
    CadastroBasicoService cadastroBasicoService;

    private static final Logger LOG = Logger.getLogger(String.valueOf(CadastroBasicoResource.class));

    @POST
    public Response create(@Valid CadastroBasicoDTO dto) {
        LOG.info("CadastroBasicoResource create");
        return Response.status(Response.Status.CREATED).entity(cadastroBasicoService.create(dto)).build();
    }
}
