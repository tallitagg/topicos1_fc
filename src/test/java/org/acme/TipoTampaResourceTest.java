package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.TipoTampaDTO;
import org.acme.dto.TipoTampaResponseDTO;
import org.acme.service.TipoTampaService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TipoTampaResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY2MDU1MjE1LCJpYXQiOjE3NjU5Njg4MTUsImp0aSI6IjZlZDA3Y2E0LWU3MTMtNGJlZC1hOTgzLWRiZGQ3ZjJkM2IwNSJ9.t3sayRQpUFp4-gpu0-O7wnVY9kBAQboqWp6BqsMaaBxleB9TqMU029HERg9phj12ga1zU4s2yG6oq5LaeuonTYWgds3CwXW03r7k8NmOfuEoFn4j04bBxss8TzTJSbLQLIXuiLv6vNSLPJZ1hBR3zNBTdrbzP04WCNwrb8QAJ1LIut0Dic7erPQQ68Ko9K0lHw1hrPYSKOXRGJz4c2gA5WUD5iaJ0EbBx6jg2Xd4HZbkoR2RsIjggwsl4BxRnruxGPn32ZhaOToG3oFUAXnvOcdcULVZxVWMC_0xMM_uigCOHYl_ptp9AYZnIryYU0c-8Jr4FWBokTLqMqnQXE_bsg ";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJtYXJpYS5zYW50b3MiLCJncm91cHMiOlsiVVNFUiJdLCJleHAiOjE3NjYwNTUyOTgsImlhdCI6MTc2NTk2ODg5OCwianRpIjoiYzRhMDljN2EtNzhmZC00YjgxLThhYmYtMjk1MzY0NDAyYTRkIn0.N5zuManJ90sz4ka1Jhllf2tudq0162KPykOYjo2mdyXZGYQP076Ksf6fs8qpMXNEsLHXUDpb752VrMaLBqyzTKSAWOJSolQBMgK59IrUXtuhpcqlXAEGIh8FNJu3ZwTMC9Ks3nHnyy-ZYvuV5o9g_ELud-0Op59UyoHttR5w_LaPMcuYC0N70IaWY32-RrF1o2s1muzFRABEp1TTEBeEe4Tek55dBHbWytN3qr8CVCRYmSkooPJRzHR3zvZ5L_5VWSUkFwbxyqKMApnsdYcQ3BkplEuYcjFptzEGcOyhEVMv3BCnkqM8h9STowFic8m8Cb7SDXdmP4GolGgbmkGDlA ";
    
    @Inject
    TipoTampaService tipoTampaService;

    @Test
    void buscarTudoTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/tipoTampas")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorDescricaoTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("descricao", "Flip-top")
                .when()
                .get("/tipoTampas/descricao/{descricao}", "Flip-top")
                .then()
                .statusCode(200)
                .body("descricao", CoreMatchers.hasItem("Flip-top"));
    }

    @Test
    void buscarPorMaterialTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("material", "PP/Tritan")
                .when()
                .get("/tipoTampas/material/{material}", "PP/Tritan")
                .then()
                .statusCode(200)
                .body("material", CoreMatchers.hasItem("PP/Tritan"));
    }

    @Test
    void incluirTest() {
        TipoTampaDTO dto = new TipoTampaDTO("Teste", "PP/Teste");

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/tipoTampas")
                .then()
                .statusCode(201)
                .body("descricao", CoreMatchers.is("Teste"))
                .body("material", CoreMatchers.is("PP/Teste"));
    }

    @Test
    void atualizarTest() {
        TipoTampaDTO dto = new TipoTampaDTO("Teste2", "PP/Teste2");

        TipoTampaResponseDTO responseDTO = tipoTampaService.create(dto);

        TipoTampaDTO dtoUpdate = new TipoTampaDTO("Teste2", "PPL/Teste2");

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/tipoTampas/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = tipoTampaService.findById(responseDTO.id());
        assertEquals(dtoUpdate.material(), responseDTO.material());
        assertEquals(dtoUpdate.descricao(), responseDTO.descricao());
    }

    @Test
    void excluirTest() {
        TipoTampaDTO dto = new TipoTampaDTO("Teste3", "PPTeste");

        TipoTampaResponseDTO responseDTO = tipoTampaService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/tipoTampas/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = tipoTampaService.findById(responseDTO.id());

        assertNull(responseDTO);
    }
}
