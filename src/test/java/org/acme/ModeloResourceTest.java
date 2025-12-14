package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.ModeloDTO;
import org.acme.dto.ModeloResponseDTO;
import org.acme.service.ModeloService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class ModeloResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY0OTk4Mjg1LCJpYXQiOjE3NjQ5MTE4ODUsImp0aSI6IjM5ZTNhY2JiLWUwNGMtNDU3Ni1iMmQ0LTgwZTZiM2NlNWRlMSJ9.PBFAarKqnkAztEGUtKN22pgRQZ0Kd0Js1WE2p4hZ1yHJWoKdHHMSKKQdcMsiLuwz4OcZRsuaUIfvjBPx2syaKb9I41KxKAAcsN3E6MRT01kHcb37eCP2OfyCbGzlqxFnrxaY6cGATn4s184TWqvbPYpkpzzN3N5VPGjj9DEwbSxQvxNdS0HUVk4givgCxr7-6hRIT413PN2PVHahfjLpDodaaH-I4FUE-HbyMv_zT0cfCkuQHlSSjUAsM2WQe2wDbiqzA0MZWQFT83ZrWXQ7dFYM0wcdHF-HAELFgEx9nOyVqETS2FMMo2xaVQ4wNZu0yjlbwdEP6-bsFJMiF9pd6g";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJqb2FvLnNpbHZhIiwiZ3JvdXBzIjpbIlVTRVIiXSwiZXhwIjoxNzY0OTk4MjM0LCJpYXQiOjE3NjQ5MTE4MzQsImp0aSI6IjhmNjM0NmFhLTUzYWItNDJhMS04N2M0LWJmOGZhOTAyY2M1ZSJ9.MH8pYsj2sgh1d1YwLcctZSGmi6mYELNyFk-q5zwIOe_X1G9SI_xwda3OsoVXlpSpMDM6rZhrGv4x_M2wfYuEYk24nX8E-4Z0jS0CbxzWmY7RvMrB4vh3I0rfPvMBvKUh1b3kTPj2jBPtn_C6zm7FzLOJ91iifGpxMR02KfIGQd8KCMZgpHuQ8BrFPuV1OwfmT_mJ1xX29gqaQnJoE_xKb9T52J0PTcLisN-Z4cTAArj_rgtobJ0PYr_9A2ui-7izdm8X7Z3AS58y8YnIOYRa683t6DL3a0Oayid70BDYZ_tq84FlJYfuXYdHI_GsbNTSgNgX-DTAMQllw1Cmsq9q5Q";

    @Inject
    ModeloService modeloService;

    @Test
    void buscarTodosTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/modelos")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorNome() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("nome", "SteelCore 750")
                .when()
                .get("/modelos/nome/{nome}")
                .then()
                .statusCode(200)
                .body("nome", CoreMatchers.hasItem("SteelCore 750"));
    }

    // TODO corrigir
    @Test
    void buscarPorMarca() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("marcaNome", "HydraPro")
                .when()
                .get("/modelos/marca/{marcaNome}")
                .then()
                .statusCode(200)
                .body("marca.nome", CoreMatchers.hasItem("HydraPro"));
    }

    @Test
    void buscarPorAnoLancamento() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("anoLancamento", 2023)
                .when()
                .get("/modelos/ano/{anoLancamento}")
                .then()
                .statusCode(200)
                .body("anoLancamento", CoreMatchers.hasItem(2023));
    }

    @Test
    void incluirTest() {
        ModeloDTO dto = new ModeloDTO("Teste", 2022,2L);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/modelos")
                .then()
                .statusCode(201)
                .body("nome", CoreMatchers.is("Teste"))
                .body("anoLancamento", CoreMatchers.is(2022));
    }

    @Test
    void updateTest() {
        ModeloDTO dto = new ModeloDTO("Teste2", 2023, 3L);

        ModeloResponseDTO responseDTO = modeloService.create(dto);

        ModeloDTO dtoUpdate = new ModeloDTO("Teste2", 2024, 3L);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/modelos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = modeloService.findById(responseDTO.id());
        assertEquals(dtoUpdate.nome(), responseDTO.nome());
        assertEquals(dtoUpdate.anoLancamento(), responseDTO.anoLancamento());
    }

    @Test
    void deleteTest() {
        ModeloDTO dto = new ModeloDTO("Teste3", 2024, 2L);
        ModeloResponseDTO responseDTO = modeloService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/modelos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = modeloService.findById(responseDTO.id());

        assertNull(responseDTO);
    }

}
