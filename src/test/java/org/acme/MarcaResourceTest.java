package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.MarcaDTO;
import org.acme.dto.MarcaResponseDTO;
import org.acme.service.MarcaService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class MarcaResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY0OTk4Mjg1LCJpYXQiOjE3NjQ5MTE4ODUsImp0aSI6IjM5ZTNhY2JiLWUwNGMtNDU3Ni1iMmQ0LTgwZTZiM2NlNWRlMSJ9.PBFAarKqnkAztEGUtKN22pgRQZ0Kd0Js1WE2p4hZ1yHJWoKdHHMSKKQdcMsiLuwz4OcZRsuaUIfvjBPx2syaKb9I41KxKAAcsN3E6MRT01kHcb37eCP2OfyCbGzlqxFnrxaY6cGATn4s184TWqvbPYpkpzzN3N5VPGjj9DEwbSxQvxNdS0HUVk4givgCxr7-6hRIT413PN2PVHahfjLpDodaaH-I4FUE-HbyMv_zT0cfCkuQHlSSjUAsM2WQe2wDbiqzA0MZWQFT83ZrWXQ7dFYM0wcdHF-HAELFgEx9nOyVqETS2FMMo2xaVQ4wNZu0yjlbwdEP6-bsFJMiF9pd6g";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJqb2FvLnNpbHZhIiwiZ3JvdXBzIjpbIlVTRVIiXSwiZXhwIjoxNzY0OTk4MjM0LCJpYXQiOjE3NjQ5MTE4MzQsImp0aSI6IjhmNjM0NmFhLTUzYWItNDJhMS04N2M0LWJmOGZhOTAyY2M1ZSJ9.MH8pYsj2sgh1d1YwLcctZSGmi6mYELNyFk-q5zwIOe_X1G9SI_xwda3OsoVXlpSpMDM6rZhrGv4x_M2wfYuEYk24nX8E-4Z0jS0CbxzWmY7RvMrB4vh3I0rfPvMBvKUh1b3kTPj2jBPtn_C6zm7FzLOJ91iifGpxMR02KfIGQd8KCMZgpHuQ8BrFPuV1OwfmT_mJ1xX29gqaQnJoE_xKb9T52J0PTcLisN-Z4cTAArj_rgtobJ0PYr_9A2ui-7izdm8X7Z3AS58y8YnIOYRa683t6DL3a0Oayid70BDYZ_tq84FlJYfuXYdHI_GsbNTSgNgX-DTAMQllw1Cmsq9q5Q";

    @Inject
    MarcaService marcaService;

    @Test
    void buscarTodosTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/marcas")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorNome() {
        RestAssured.given()
                .pathParam("nome", "HydraPro")
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/marcas/{nome}")
                .then()
                .statusCode(200)
                .body("nome", CoreMatchers.hasItem("HydraPro"));
    }

    @Test
    void incluirTest() {
        MarcaDTO dto = new MarcaDTO("Stanley", List.of(2L, 1L));

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/marcas")
                .then()
                .statusCode(201)
                .body("nome", CoreMatchers.is("Stanley"))
                .body("modelos.id", CoreMatchers.hasItems(1, 2));
    }

    @Test
    void alterarTest() {
        MarcaDTO dto = new MarcaDTO("Stanley", List.of(2L, 1L));
        MarcaResponseDTO responseDTO = marcaService.create(dto);

        MarcaDTO dtoUpdate = new MarcaDTO("Stanley 3D", List.of(4L));

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/marcas/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = marcaService.findById(responseDTO.id());
        assertEquals(dtoUpdate.nome(), responseDTO.nome());
        assertEquals(dtoUpdate.modeloIds(), responseDTO.modelos());
    }

    @Test
    void excluirTest() {
        MarcaDTO dto = new MarcaDTO("HotCool", List.of(10L));
        MarcaResponseDTO responseDTO = marcaService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/marcas/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = marcaService.findById(responseDTO.id());
        assertNull(responseDTO);
    }


}
