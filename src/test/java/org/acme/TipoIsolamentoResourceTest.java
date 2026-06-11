package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.TipoIsolamentoDTO;
import org.acme.dto.TipoIsolamentoResponseDTO;
import org.acme.service.TipoIsolamentoService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TipoIsolamentoResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY2MDU1MjE1LCJpYXQiOjE3NjU5Njg4MTUsImp0aSI6IjZlZDA3Y2E0LWU3MTMtNGJlZC1hOTgzLWRiZGQ3ZjJkM2IwNSJ9.t3sayRQpUFp4-gpu0-O7wnVY9kBAQboqWp6BqsMaaBxleB9TqMU029HERg9phj12ga1zU4s2yG6oq5LaeuonTYWgds3CwXW03r7k8NmOfuEoFn4j04bBxss8TzTJSbLQLIXuiLv6vNSLPJZ1hBR3zNBTdrbzP04WCNwrb8QAJ1LIut0Dic7erPQQ68Ko9K0lHw1hrPYSKOXRGJz4c2gA5WUD5iaJ0EbBx6jg2Xd4HZbkoR2RsIjggwsl4BxRnruxGPn32ZhaOToG3oFUAXnvOcdcULVZxVWMC_0xMM_uigCOHYl_ptp9AYZnIryYU0c-8Jr4FWBokTLqMqnQXE_bsg ";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJtYXJpYS5zYW50b3MiLCJncm91cHMiOlsiVVNFUiJdLCJleHAiOjE3NjYwNTUyOTgsImlhdCI6MTc2NTk2ODg5OCwianRpIjoiYzRhMDljN2EtNzhmZC00YjgxLThhYmYtMjk1MzY0NDAyYTRkIn0.N5zuManJ90sz4ka1Jhllf2tudq0162KPykOYjo2mdyXZGYQP076Ksf6fs8qpMXNEsLHXUDpb752VrMaLBqyzTKSAWOJSolQBMgK59IrUXtuhpcqlXAEGIh8FNJu3ZwTMC9Ks3nHnyy-ZYvuV5o9g_ELud-0Op59UyoHttR5w_LaPMcuYC0N70IaWY32-RrF1o2s1muzFRABEp1TTEBeEe4Tek55dBHbWytN3qr8CVCRYmSkooPJRzHR3zvZ5L_5VWSUkFwbxyqKMApnsdYcQ3BkplEuYcjFptzEGcOyhEVMv3BCnkqM8h9STowFic8m8Cb7SDXdmP4GolGgbmkGDlA ";
    
    @Inject
    TipoIsolamentoService tipoIsolamentoService;

    @Test
    void buscarTodosTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/tipoIsolamentos")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorDescricaoTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("descricao", "Gel Pack")
                .when()
                .get("tipoIsolamentos/descricao/{descricao}")
                .then()
                .statusCode(200)
                .body("descricao", CoreMatchers.hasItem("Gel Pack"));
    }

    @Test
    void buscarPorEficienciaTermicaTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("eficienciaTermica", 92)
                .when()
                .get("/tipoIsolamentos/eficienciaTermica/{eficienciaTermica}")
                .then()
                .statusCode(200)
                .body("eficienciaTermica", CoreMatchers.hasItem(92F));
    }

    @Test
    void incluirTest() {
        TipoIsolamentoDTO dto = new TipoIsolamentoDTO("Testando", 30.0);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/tipoIsolamentos/")
                .then()
                .statusCode(201)
                .body("descricao", CoreMatchers.is("Testando"))
                .body("eficienciaTermica", CoreMatchers.is(30F));
    }

    @Test
    void atualizarTest() {
        TipoIsolamentoDTO dto = new TipoIsolamentoDTO("Testando2", 31.0);

        TipoIsolamentoResponseDTO responseDTO = tipoIsolamentoService.create(dto);

        TipoIsolamentoDTO dtoUpdate = new TipoIsolamentoDTO("Testando3", 31.0);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/tipoIsolamentos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = tipoIsolamentoService.findById(responseDTO.id());
        assertEquals(dtoUpdate.descricao(), responseDTO.descricao());
        assertEquals(dtoUpdate.eficienciaTermica(), responseDTO.eficienciaTermica());
    }

    @Test
    void excluirTest() {
        TipoIsolamentoDTO dto = new TipoIsolamentoDTO("Testando4", 31.0);

        TipoIsolamentoResponseDTO responseDTO = tipoIsolamentoService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/tipoIsolamentos/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = tipoIsolamentoService.findById(responseDTO.id());

        assertNull(responseDTO);
    }
}
