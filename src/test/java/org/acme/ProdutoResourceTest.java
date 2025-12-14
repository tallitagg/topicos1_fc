package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.ProdutoDTO;
import org.acme.dto.ProdutoResponseDTO;
import org.acme.service.ProdutoService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class ProdutoResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY0OTk4Mjg1LCJpYXQiOjE3NjQ5MTE4ODUsImp0aSI6IjM5ZTNhY2JiLWUwNGMtNDU3Ni1iMmQ0LTgwZTZiM2NlNWRlMSJ9.PBFAarKqnkAztEGUtKN22pgRQZ0Kd0Js1WE2p4hZ1yHJWoKdHHMSKKQdcMsiLuwz4OcZRsuaUIfvjBPx2syaKb9I41KxKAAcsN3E6MRT01kHcb37eCP2OfyCbGzlqxFnrxaY6cGATn4s184TWqvbPYpkpzzN3N5VPGjj9DEwbSxQvxNdS0HUVk4givgCxr7-6hRIT413PN2PVHahfjLpDodaaH-I4FUE-HbyMv_zT0cfCkuQHlSSjUAsM2WQe2wDbiqzA0MZWQFT83ZrWXQ7dFYM0wcdHF-HAELFgEx9nOyVqETS2FMMo2xaVQ4wNZu0yjlbwdEP6-bsFJMiF9pd6g";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJqb2FvLnNpbHZhIiwiZ3JvdXBzIjpbIlVTRVIiXSwiZXhwIjoxNzY0OTk4MjM0LCJpYXQiOjE3NjQ5MTE4MzQsImp0aSI6IjhmNjM0NmFhLTUzYWItNDJhMS04N2M0LWJmOGZhOTAyY2M1ZSJ9.MH8pYsj2sgh1d1YwLcctZSGmi6mYELNyFk-q5zwIOe_X1G9SI_xwda3OsoVXlpSpMDM6rZhrGv4x_M2wfYuEYk24nX8E-4Z0jS0CbxzWmY7RvMrB4vh3I0rfPvMBvKUh1b3kTPj2jBPtn_C6zm7FzLOJ91iifGpxMR02KfIGQd8KCMZgpHuQ8BrFPuV1OwfmT_mJ1xX29gqaQnJoE_xKb9T52J0PTcLisN-Z4cTAArj_rgtobJ0PYr_9A2ui-7izdm8X7Z3AS58y8YnIOYRa683t6DL3a0Oayid70BDYZ_tq84FlJYfuXYdHI_GsbNTSgNgX-DTAMQllw1Cmsq9q5Q";

    @Inject
    ProdutoService produtoService;

    @Test
    void buscarTodos() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorNome() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("nome", "Frost 500 Inox")
                .when()
                .get("/produtos/nome/{nome}")
                .then()
                .statusCode(200)
                .body("nome", CoreMatchers.hasItem("Frost 500 Inox"));
    }

    @Test
    void buscarPorPreco() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("preco", 149)
                .when()
                .get("/produtos/preco/{preco}")
                .then()
                .statusCode(200)
                .body("preco", CoreMatchers.hasItem(149));
    }

    @Test
    void buscarPorMarca() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("marca", "HydraPro")
                .when()
                .get("/produtos/marca/{marca}")
                .then()
                .statusCode(200)
                .body("marca.nome", CoreMatchers.hasItem("HydraPro"));
    }

    @Test
    void buscarPorModelo() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("modelo", "Kids Pop 350")
                .when()
                .get("/produtos/modelo/{modelo}")
                .then()
                .statusCode(200)
                .body("modelo.nome", CoreMatchers.hasItem("Kids Pop 350"));
    }

    @Test
    void buscarPorMaterial() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("material", "Aço Inox 304 (corpo externo escovado)")
                .when()
                .get("/produtos/material/{material}")
                .then()
                .statusCode(200)
                .body("material.tipo", CoreMatchers.hasItem("Aço Inox 304 (corpo externo escovado)"));
    }

    @Test
    void buscarPorCapacidade() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("capacidade", 0.75)
                .when()
                .get("/produtos/capacidade/{capacidade}")
                .then()
                .statusCode(200)
                .body("capacidade", CoreMatchers.hasItem(0.75F));
    }

    @Test
    void incluirTest() {
        ProdutoDTO dto = new ProdutoDTO(
                "Garrafa Térmica Thermo 500",
                "Garrafa inox com isolamento duplo",
                250D,
                0.5,
                null, 1L,
                1L,
                1L,
                1L,
                1L,
                List.of(1L)
        );

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/produtos")
                .then()
                .statusCode(201)
                .body("nome", CoreMatchers.is("Garrafa Térmica Thermo 500"))
                .body("descricao", CoreMatchers.is("Garrafa inox com isolamento duplo"))
                .body("capacidade", CoreMatchers.is(0.5F))
                .body("marca.id", CoreMatchers.is(1))
                .body("modelo.id", CoreMatchers.is(1));
    }

    @Test
    void atualizarTest() {
        ProdutoDTO dto = new ProdutoDTO(
                "Garrafa Térmica Frost 500",
                "Garrafa inox com tampa plástica",
                200D,
                0.5,
                null, 1L,
                1L,
                1L,
                1L,
                1L,
                List.of(1L)
        );

        ProdutoResponseDTO responseDTO = produtoService.create(dto);

        ProdutoDTO dtoUpdate = new ProdutoDTO(
                "Garrafa Térmica Frost 500 Plus",
                "Nova versão com isolamento reforçado",
                250D,
                0.75,
                null, 1L,
                1L,
                1L,
                1L,
                1L,
                List.of(1L)
        );

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/produtos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = produtoService.findById(responseDTO.id());

        assertEquals(dtoUpdate.nome(), responseDTO.nome());
        assertEquals(dtoUpdate.descricao(), responseDTO.descricao());
        assertEquals(dtoUpdate.preco(), responseDTO.preco());
        assertEquals(dtoUpdate.capacidade(), responseDTO.capacidade());
    }

    @Test
    void excluirTest() {
        ProdutoDTO dto = new ProdutoDTO(
                "Garrafa Térmica Steel 750",
                "Garrafa inox de 750ml com tampa de pressão",
                300D,
                0.75,
                null, 1L,
                1L,
                1L,
                1L,
                1L,
                List.of(1L)
        );

        ProdutoResponseDTO responseDTO = produtoService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/produtos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = produtoService.findById(responseDTO.id());

        assertNull(responseDTO);
    }


}
