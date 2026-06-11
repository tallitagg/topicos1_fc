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

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY2MDU1MjE1LCJpYXQiOjE3NjU5Njg4MTUsImp0aSI6IjZlZDA3Y2E0LWU3MTMtNGJlZC1hOTgzLWRiZGQ3ZjJkM2IwNSJ9.t3sayRQpUFp4-gpu0-O7wnVY9kBAQboqWp6BqsMaaBxleB9TqMU029HERg9phj12ga1zU4s2yG6oq5LaeuonTYWgds3CwXW03r7k8NmOfuEoFn4j04bBxss8TzTJSbLQLIXuiLv6vNSLPJZ1hBR3zNBTdrbzP04WCNwrb8QAJ1LIut0Dic7erPQQ68Ko9K0lHw1hrPYSKOXRGJz4c2gA5WUD5iaJ0EbBx6jg2Xd4HZbkoR2RsIjggwsl4BxRnruxGPn32ZhaOToG3oFUAXnvOcdcULVZxVWMC_0xMM_uigCOHYl_ptp9AYZnIryYU0c-8Jr4FWBokTLqMqnQXE_bsg ";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJtYXJpYS5zYW50b3MiLCJncm91cHMiOlsiVVNFUiJdLCJleHAiOjE3NjYwNTUyOTgsImlhdCI6MTc2NTk2ODg5OCwianRpIjoiYzRhMDljN2EtNzhmZC00YjgxLThhYmYtMjk1MzY0NDAyYTRkIn0.N5zuManJ90sz4ka1Jhllf2tudq0162KPykOYjo2mdyXZGYQP076Ksf6fs8qpMXNEsLHXUDpb752VrMaLBqyzTKSAWOJSolQBMgK59IrUXtuhpcqlXAEGIh8FNJu3ZwTMC9Ks3nHnyy-ZYvuV5o9g_ELud-0Op59UyoHttR5w_LaPMcuYC0N70IaWY32-RrF1o2s1muzFRABEp1TTEBeEe4Tek55dBHbWytN3qr8CVCRYmSkooPJRzHR3zvZ5L_5VWSUkFwbxyqKMApnsdYcQ3BkplEuYcjFptzEGcOyhEVMv3BCnkqM8h9STowFic8m8Cb7SDXdmP4GolGgbmkGDlA ";
    
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