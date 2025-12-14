package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.dto.MaterialDTO;
import org.acme.dto.MaterialResponseDTO;
import org.acme.service.MaterialService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class MaterialResourceTest {

    String tokenAdm = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJhZG1pbi5nZXJhbCIsImdyb3VwcyI6WyJBRE0iXSwiZXhwIjoxNzY0OTk4Mjg1LCJpYXQiOjE3NjQ5MTE4ODUsImp0aSI6IjM5ZTNhY2JiLWUwNGMtNDU3Ni1iMmQ0LTgwZTZiM2NlNWRlMSJ9.PBFAarKqnkAztEGUtKN22pgRQZ0Kd0Js1WE2p4hZ1yHJWoKdHHMSKKQdcMsiLuwz4OcZRsuaUIfvjBPx2syaKb9I41KxKAAcsN3E6MRT01kHcb37eCP2OfyCbGzlqxFnrxaY6cGATn4s184TWqvbPYpkpzzN3N5VPGjj9DEwbSxQvxNdS0HUVk4givgCxr7-6hRIT413PN2PVHahfjLpDodaaH-I4FUE-HbyMv_zT0cfCkuQHlSSjUAsM2WQe2wDbiqzA0MZWQFT83ZrWXQ7dFYM0wcdHF-HAELFgEx9nOyVqETS2FMMo2xaVQ4wNZu0yjlbwdEP6-bsFJMiF9pd6g";

    String tokenUser = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJ0b3BpY29zXzEiLCJzdWIiOiJqb2FvLnNpbHZhIiwiZ3JvdXBzIjpbIlVTRVIiXSwiZXhwIjoxNzY0OTk4MjM0LCJpYXQiOjE3NjQ5MTE4MzQsImp0aSI6IjhmNjM0NmFhLTUzYWItNDJhMS04N2M0LWJmOGZhOTAyY2M1ZSJ9.MH8pYsj2sgh1d1YwLcctZSGmi6mYELNyFk-q5zwIOe_X1G9SI_xwda3OsoVXlpSpMDM6rZhrGv4x_M2wfYuEYk24nX8E-4Z0jS0CbxzWmY7RvMrB4vh3I0rfPvMBvKUh1b3kTPj2jBPtn_C6zm7FzLOJ91iifGpxMR02KfIGQd8KCMZgpHuQ8BrFPuV1OwfmT_mJ1xX29gqaQnJoE_xKb9T52J0PTcLisN-Z4cTAArj_rgtobJ0PYr_9A2ui-7izdm8X7Z3AS58y8YnIOYRa683t6DL3a0Oayid70BDYZ_tq84FlJYfuXYdHI_GsbNTSgNgX-DTAMQllw1Cmsq9q5Q";

    @Inject
    MaterialService materialService;

    @Test
    void buscarTodosTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .when()
                .get("/materiais")
                .then()
                .statusCode(200);
    }

    @Test
    void buscarPorTipoTest() {
        RestAssured.given()
                .header("Authorization", "Bearer " + tokenUser)
                .pathParam("tipo", "Aço Inox 316L (corpo interno, alta resistência à corrosão)")
                .when()
                .get("/materiais/{tipo}")
                .then()
                .statusCode(200)
                .body("tipo", CoreMatchers.hasItem("Aço Inox 316L (corpo interno, alta resistência à corrosão)"));
    }

    @Test
    void incluirTest() {
        MaterialDTO dto = new MaterialDTO("Aço Inox 320L", 250.0);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/materiais")
                .then()
                .statusCode(201)
                .body("tipo", CoreMatchers.is("Aço Inox 320L"))
                .body("resistenciaTemperatura", CoreMatchers.is(250.0F));
    }

    @Test
    void atualizarTest() {
        MaterialDTO dto = new MaterialDTO("Aço Inox 316L (corpo interno, alta resistência à corrosão)", 160.0);

        MaterialResponseDTO responseDTO = materialService.create(dto);

        MaterialDTO dtoUpdate = new MaterialDTO("Aço Inox 320L", 250.0);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .contentType(ContentType.JSON)
                .body(dtoUpdate)
                .when()
                .put("/materiais/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = materialService.findById(responseDTO.id());
        assertEquals(dtoUpdate.tipo(), responseDTO.tipo());
        assertEquals(dtoUpdate.resistenciaTemperatura(), responseDTO.resistenciaTemperatura());
    }

    @Test
    void excluirTest() {
        MaterialDTO dto = new MaterialDTO("Aço Inox 320L", 250.0);
        MaterialResponseDTO responseDTO = materialService.create(dto);

        RestAssured.given()
                .header("Authorization", "Bearer " + tokenAdm)
                .when()
                .delete("/materiais/" + responseDTO.id())
                .then()
                .statusCode(204);

        responseDTO = materialService.findById(responseDTO.id());

        assertNull(responseDTO);
    }
}