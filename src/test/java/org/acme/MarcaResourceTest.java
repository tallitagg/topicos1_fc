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

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY2MDU1MjE1LCJpYXQiOjE3NjU5Njg4MTUsImp0aSI6IjZlZDA3Y2E0LWU3MTMtNGJlZC1hOTgzLWRiZGQ3ZjJkM2IwNSJ9.t3sayRQpUFp4-gpu0-O7wnVY9kBAQboqWp6BqsMaaBxleB9TqMU029HERg9phj12ga1zU4s2yG6oq5LaeuonTYWgds3CwXW03r7k8NmOfuEoFn4j04bBxss8TzTJSbLQLIXuiLv6vNSLPJZ1hBR3zNBTdrbzP04WCNwrb8QAJ1LIut0Dic7erPQQ68Ko9K0lHw1hrPYSKOXRGJz4c2gA5WUD5iaJ0EbBx6jg2Xd4HZbkoR2RsIjggwsl4BxRnruxGPn32ZhaOToG3oFUAXnvOcdcULVZxVWMC_0xMM_uigCOHYl_ptp9AYZnIryYU0c-8Jr4FWBokTLqMqnQXE_bsg ";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJtYXJpYS5zYW50b3MiLCJncm91cHMiOlsiVVNFUiJdLCJleHAiOjE3NjYwNTUyOTgsImlhdCI6MTc2NTk2ODg5OCwianRpIjoiYzRhMDljN2EtNzhmZC00YjgxLThhYmYtMjk1MzY0NDAyYTRkIn0.N5zuManJ90sz4ka1Jhllf2tudq0162KPykOYjo2mdyXZGYQP076Ksf6fs8qpMXNEsLHXUDpb752VrMaLBqyzTKSAWOJSolQBMgK59IrUXtuhpcqlXAEGIh8FNJu3ZwTMC9Ks3nHnyy-ZYvuV5o9g_ELud-0Op59UyoHttR5w_LaPMcuYC0N70IaWY32-RrF1o2s1muzFRABEp1TTEBeEe4Tek55dBHbWytN3qr8CVCRYmSkooPJRzHR3zvZ5L_5VWSUkFwbxyqKMApnsdYcQ3BkplEuYcjFptzEGcOyhEVMv3BCnkqM8h9STowFic8m8Cb7SDXdmP4GolGgbmkGDlA ";
    
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
