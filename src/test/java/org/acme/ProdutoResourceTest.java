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

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY2MDU1MjE1LCJpYXQiOjE3NjU5Njg4MTUsImp0aSI6IjZlZDA3Y2E0LWU3MTMtNGJlZC1hOTgzLWRiZGQ3ZjJkM2IwNSJ9.t3sayRQpUFp4-gpu0-O7wnVY9kBAQboqWp6BqsMaaBxleB9TqMU029HERg9phj12ga1zU4s2yG6oq5LaeuonTYWgds3CwXW03r7k8NmOfuEoFn4j04bBxss8TzTJSbLQLIXuiLv6vNSLPJZ1hBR3zNBTdrbzP04WCNwrb8QAJ1LIut0Dic7erPQQ68Ko9K0lHw1hrPYSKOXRGJz4c2gA5WUD5iaJ0EbBx6jg2Xd4HZbkoR2RsIjggwsl4BxRnruxGPn32ZhaOToG3oFUAXnvOcdcULVZxVWMC_0xMM_uigCOHYl_ptp9AYZnIryYU0c-8Jr4FWBokTLqMqnQXE_bsg ";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJtYXJpYS5zYW50b3MiLCJncm91cHMiOlsiVVNFUiJdLCJleHAiOjE3NjYwNTUyOTgsImlhdCI6MTc2NTk2ODg5OCwianRpIjoiYzRhMDljN2EtNzhmZC00YjgxLThhYmYtMjk1MzY0NDAyYTRkIn0.N5zuManJ90sz4ka1Jhllf2tudq0162KPykOYjo2mdyXZGYQP076Ksf6fs8qpMXNEsLHXUDpb752VrMaLBqyzTKSAWOJSolQBMgK59IrUXtuhpcqlXAEGIh8FNJu3ZwTMC9Ks3nHnyy-ZYvuV5o9g_ELud-0Op59UyoHttR5w_LaPMcuYC0N70IaWY32-RrF1o2s1muzFRABEp1TTEBeEe4Tek55dBHbWytN3qr8CVCRYmSkooPJRzHR3zvZ5L_5VWSUkFwbxyqKMApnsdYcQ3BkplEuYcjFptzEGcOyhEVMv3BCnkqM8h9STowFic8m8Cb7SDXdmP4GolGgbmkGDlA ";
    
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
                15,
                1L,
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
                1,
                1L,
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
                1,
                1L,
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
                1,
                1L,
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